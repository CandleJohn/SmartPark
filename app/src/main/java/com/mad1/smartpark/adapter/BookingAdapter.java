package com.mad1.smartpark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad1.smartpark.R;
import com.mad1.smartpark.interfaces.ExpressInterface;
import com.mad1.smartpark.model.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    Context context;
    List<Booking> list;

    public BookingAdapter(Context context){
        this.context = context;
    }

    public void setBookings(List<Booking> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BookingAdapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking, parent, false);
        return new BookingViewHolder(view).bookingAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.BookingViewHolder holder, int position) {
        Booking booking = list.get(position);
        holder.bookingTextView.setText(Integer.toString(booking.getBookingId()));
        holder.dateTextView.setText(booking.getTime());
        holder.costTextView.setText(Double.toString(booking.getCost()));
        holder.registrationTextView.setText(booking.getRegistration());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView bookingTextView, dateTextView, costTextView, registrationTextView;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5002/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExpressInterface api = retrofit.create(ExpressInterface.class);
        private BookingAdapter adapter;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingTextView = itemView.findViewById(R.id.bookingTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            costTextView = itemView.findViewById(R.id.costTextView);
            registrationTextView = itemView.findViewById(R.id.regTextView);

        }

        public BookingViewHolder bookingAdapter(BookingAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}
