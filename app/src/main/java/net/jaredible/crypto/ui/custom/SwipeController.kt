package net.jaredible.crypto.ui.custom

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("ClickableViewAccessibility")
class SwipeController(private var buttonsActions: SwipeControllerActions? = null) :
    ItemTouchHelper.Callback() {

    companion object {
        private const val buttonWidth = 300
    }

    private var swipeBack = false
    private var buttonShowedState = ButtonsState.GONE
    private var buttonInstance: RectF? = null
    private var currentItemViewHolder: RecyclerView.ViewHolder? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = makeMovementFlags(0, LEFT or RIGHT)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE
            0
        } else super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        var dx = dX

        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dx = Math.max(dx, buttonWidth.toFloat())
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) dx = Math.min(dx, -buttonWidth.toFloat())
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dx,
                    dY,
                    actionState,
                    isCurrentlyActive
                );
            } else {
                setTouchListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dx,
                    dY,
                    actionState,
                    isCurrentlyActive
                );
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dx, dY, actionState, isCurrentlyActive);
        }

        currentItemViewHolder = viewHolder;
    }

    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(View.OnTouchListener { v, event ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (dX < -buttonWidth.toFloat()) buttonShowedState = ButtonsState.RIGHT_VISIBLE
                else if (dX > buttonWidth.toFloat()) buttonShowedState = ButtonsState.LEFT_VISIBLE

                if (buttonShowedState != ButtonsState.GONE) {
                    setTouchDownListener(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    setItemsClickable(recyclerView, false)
                }
            }
            return@OnTouchListener false
        })
    }

    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
            return@OnTouchListener false
        })
    }

    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    0f,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                recyclerView.setOnTouchListener { v, event -> false }
                setItemsClickable(recyclerView, true)
                swipeBack = false
                buttonShowedState = ButtonsState.GONE

                if (buttonsActions != null && buttonInstance != null && buttonInstance!!.contains(
                        event.x,
                        event.y
                    )
                ) {
                    if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                        buttonsActions!!.onLeftClicked(viewHolder.absoluteAdapterPosition)
                    } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                        buttonsActions!!.onRightClicked(viewHolder.absoluteAdapterPosition)
                    }
                }
                buttonShowedState = ButtonsState.GONE
                currentItemViewHolder = null
            }
            return@OnTouchListener false
        })
    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        recyclerView.children.forEach { it.isClickable = isClickable }
    }

    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16

        val itemView: View = viewHolder.itemView
        val p = Paint()

        val leftButton = RectF(
            itemView.left.toFloat(),
            itemView.top.toFloat(),
            itemView.left.toFloat() + buttonWidthWithoutPadding,
            itemView.bottom.toFloat()
        )
        p.color = Color.BLUE
        c.drawRoundRect(leftButton, corners.toFloat(), corners.toFloat(), p)
        drawText("EDIT", c, leftButton, p)

        val rightButton = RectF(
            itemView.right.toFloat() - buttonWidthWithoutPadding,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat()
        )
        p.color = Color.RED
        c.drawRoundRect(rightButton, corners.toFloat(), corners.toFloat(), p)
        drawText("DELETE", c, rightButton, p)

        buttonInstance = null
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
        }
    }

    private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 60
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize.toFloat()

        val textWidth = p.measureText(text)
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p)
    }

    fun onDraw(c: Canvas) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder!!)
        }
    }

    enum class ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }

}