package com.example.notificationsapp.handlers

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationsapp.R
import com.example.notificationsapp.adapter.ListFragmentAdapter


class SwipeDeleteCallback(val adapter: ListFragmentAdapter) :
    ItemTouchHelper.SimpleCallback(0, /*ItemTouchHelper.RIGHT or*/ ItemTouchHelper.LEFT) {

    companion object {
        private const val BACKGROUND_CORNER_OFFSET = 50
    }

    private val icon: Drawable
    private val background: ColorDrawable

    init {
        icon = ContextCompat.getDrawable(adapter.context!!, R.drawable.ic_delete_white_36dp)!!
        background = ColorDrawable(ContextCompat.getColor(adapter.context!!, R.color.colorAccent))

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false //Não interessa dar suporte a items para cima e para baixo
        //O primeiro parametro do construtor é para isto
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        adapter.deleteItem(position)

        //Para que volte à posicao inicial
        //adapter.notifyItemChanged(position)
    }

    //Para desenhar a vista no local correto conforme é deito o swipe
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView

        //Para puxar a vista para fora dos cantos (os cantos arredondados senão via-se por tras
        // deve ser tanto maioir quanto maior o valor de corner radius do card layout
        val backgroundCornerOffset = BACKGROUND_CORNER_OFFSET

        //Colocar o icon - definir a posicao do icon
        val iconMargin = (itemView.height - icon.getIntrinsicHeight()) / 2
        val iconTop = itemView.top + (itemView.height - icon.getIntrinsicHeight()) / 2
        val iconBottom = iconTop + icon.getIntrinsicHeight()

        /*if (dX > 0) { // Swiping to the right

        } else*/
        if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon.getIntrinsicWidth()
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)

    }
}