package com.example.emp3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView

class BoxAdapter internal constructor(var ctx: Context, products: ArrayList<Product>) :
    BaseAdapter() {
    var lInflater: LayoutInflater
    var objects: ArrayList<Product>

    // кол-во элементов
    override fun getCount(): Int {
        return objects.size
    }

    // элемент по позиции
    override fun getItem(position: Int): Any {
        return objects[position]
    }

    // id по позиции
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // пункт списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        // используем созданные, но не используемые view
        var view = convertView
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false)
        }
        val p = getProduct(position)

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        (view!!.findViewById<View>(R.id.tvDescr) as TextView).text = p.name
        (view!!.findViewById<View>(R.id.tvPrice) as TextView).setText(p.getPriceString())
        (view!!.findViewById<View>(R.id.ivImage) as ImageView).setImageResource(p.image)
        val cbBuy = view!!.findViewById<View>(R.id.cbBox) as CheckBox
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList)
        // пишем позицию
        cbBuy.tag = position
        // заполняем данными из товаров: в корзине или нет
        cbBuy.isChecked = p.box
        return view
    }

    // товар по позиции
    fun getProduct(position: Int): Product {
        return getItem(position) as Product
    }

    fun getBox(): java.util.ArrayList<Product>? {
        val box = java.util.ArrayList<Product>()
        for (p in objects) {
            // если в корзине
            if (p.box) box.add(p)
        }
        return box
    }

    // обработчик для чекбоксов
    var myCheckChangeList =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> // меняем данные товара (в корзине или нет)
            getProduct(buttonView.tag as Int).box = isChecked
        }

    init {
        objects = products
        lInflater = ctx
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}
