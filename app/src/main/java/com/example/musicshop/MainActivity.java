package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
int q=0;
        HashMap goodsMap;//переменная куда записываем название товаров и их цену
        Spinner spinner;//переменная для взаимосвязи со списком в разметке xml
        ArrayList spinnerArrayList;//переменная для взаимосвязи со списком в разметке xml
        ArrayAdapter spinnerAdapter;//Адаптер
        String goodsName;//Название товара
        Double price;//Цена товара
        EditText userNameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText=findViewById(R.id.editText);
        spinner=findViewById(R.id.spinner); // присваиваем переменной класса spinner список из xml разметки
        spinner.setOnItemSelectedListener(this);

        spinnerArrayList= new ArrayList();//создаем объект класса словарь
        //добавляем в словарь значения
        spinnerArrayList.add("guitar");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("keyboard");

        //Адаптер нужен для того чтобы перенести значения из жавакода в xml
        spinnerAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,spinnerArrayList);//Заводим адаптер которому
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);//присваиваем значения нашего словаря
        spinner.setAdapter(spinnerAdapter);//устанавливаем адаптер

        //хэшмэп
        goodsMap=new HashMap();
        goodsMap.put("guitar",500.0);
        goodsMap.put("drums",1500.0);
        goodsMap.put("keyboard",1000.0);
    }

    public void increase(View view) {
        q+=1;
        TextView txtincrease=findViewById(R.id.textView5);
        txtincrease.setText(""+q);
        TextView priceTextView=findViewById(R.id.textView3);
        priceTextView.setText(""+q*price);
    }

    public void decrease(View view) {
        q-=1;
        if(q<0){
            q=0;
        }
        TextView txtincrease=findViewById(R.id.textView5);
        txtincrease.setText(""+q);
        TextView priceTextView=findViewById(R.id.textView3);
        priceTextView.setText(""+q*price);
    }

    @Override
    //Что происходит когда мы выбираем что то в списке
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Переменной goodsName присваиваем элемент списка который выбран в данный момент
       goodsName=spinner.getSelectedItem().toString();

       //Цену береи из HashMap по идентификатору
       price=(Double)goodsMap.get(goodsName);

       //Записываем в переменную класса textview значение которое находится в textview с ценой в xml, или связываем их
       TextView priceTextView=findViewById(R.id.textView3);

       //И выводим цену(произведение цены взятой из HashMap на кол-во товаров записанных в переменной q
       priceTextView.setText(""+q*price);

        ImageView goodsImageView=findViewById(R.id.goodsImageView);
        switch (goodsName){
            case "guitar":
                goodsImageView.setImageResource(R.drawable.guitar2);
                break;
            case "drums":
                goodsImageView.setImageResource(R.drawable.drums);
                break;
            case "keyboard":
                goodsImageView.setImageResource(R.drawable.keyboard);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.guitar2);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {
        Order order = new Order();
        order.userName=userNameEditText.getText().toString();
        order.goodsName=goodsName;
        order.quantity=q;
        order.orderPrice=q*price;
        order.price=price;

        Intent orderIntent = new Intent(MainActivity.this,OrderActivity.class);
        orderIntent.putExtra("userNameForIntent",order.userName);
        orderIntent.putExtra("goodsName",order.goodsName);
        orderIntent.putExtra("quantity",order.quantity);
        orderIntent.putExtra("orderPrice",order.orderPrice);
        orderIntent.putExtra("price",order.price);
        startActivity(orderIntent);
    }
}
