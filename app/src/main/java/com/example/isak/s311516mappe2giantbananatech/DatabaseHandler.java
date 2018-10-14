package com.example.isak.s311516mappe2giantbananatech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHandler {

    public static final class FriendsTable implements BaseColumns{
        public static final String TABLE_NAME = "friends";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
    }

    public static final class BookingsTable implements BaseColumns{
        public static final String TABLE_NAME = "bookings";
        public static final String COLUMN_NAME_RESTAURANT = "restaurant";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
    }

    public static final class FriendsInBookingTable implements BaseColumns{
        public static final String TABLE_NAME = "friends_in_booking";
        public static final String COLUMN_NAME_FRIEND_ID = "friend_id";
        public static final String COLUMN_NAME_BOOKING_ID = "booking_id";
    }

    public static final class RestaurantsTable implements BaseColumns {
        public static final String TABLE_NAME = "restaurants";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_TYPE = "type";
    }

    private static final String SQL_CREATE_TABLE_FRIENDS = "CREATE TABLE IF NOT EXISTS " + FriendsTable.TABLE_NAME + " (" +
            FriendsTable._ID + " INTEGER PRIMARY KEY, " +
            FriendsTable.COLUMN_NAME_NAME + " TEXT, " +
            FriendsTable.COLUMN_NAME_PHONE + " TEXT);";

    private static final String SQL_CREATE_TABLE_BOOKINGS = "CREATE TABLE IF NOT EXISTS " + BookingsTable.TABLE_NAME + " (" +
            BookingsTable._ID + " INTEGER PRIMARY KEY, " +
            BookingsTable.COLUMN_NAME_RESTAURANT + " TEXT, " +
            BookingsTable.COLUMN_NAME_DATE + " TEXT, " +
            BookingsTable.COLUMN_NAME_TIME + " TEXT);";

    private static final String SQL_CREATE_TABLE_FRIENDS_IN_BOOKING = "CREATE TABLE IF NOT EXISTS " + FriendsInBookingTable.TABLE_NAME + " (" +
            FriendsInBookingTable.COLUMN_NAME_FRIEND_ID + " INTEGER, " +
            FriendsInBookingTable.COLUMN_NAME_BOOKING_ID + " INTEGER);";

    private static final String SQL_CREATE_TABLE_RESTAURANTS = "CREATE TABLE IF NOT EXISTS " + RestaurantsTable.TABLE_NAME + " (" +
            RestaurantsTable._ID + " INTEGER PRIMARY KEY, " +
            RestaurantsTable.COLUMN_NAME_NAME + " TEXT, " +
            RestaurantsTable.COLUMN_NAME_ADDRESS + " TEXT, " +
            RestaurantsTable.COLUMN_NAME_PHONE + " TEXT, " +
            RestaurantsTable.COLUMN_NAME_TYPE + " TEXT);";

    private static final String SQL_SELECT_ALL_FRIENDS_IN_BOOKING = "SELECT " + FriendsTable._ID + ", " + FriendsTable.COLUMN_NAME_NAME + ", " +
            FriendsTable.COLUMN_NAME_PHONE + " FROM " + FriendsTable.TABLE_NAME + ", "  + FriendsInBookingTable.TABLE_NAME + " WHERE " +
            FriendsTable.TABLE_NAME + "." + FriendsTable._ID + " == " + FriendsInBookingTable.COLUMN_NAME_FRIEND_ID + " AND " +
            FriendsInBookingTable.COLUMN_NAME_BOOKING_ID + " == ?";

    public static class DbHelper extends SQLiteOpenHelper{
        private static DbHelper dbHelper;
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "BookingAPP.DB";

        private DbHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        public static synchronized DbHelper getInstance(Context context){
            if (dbHelper==null){
                dbHelper = new DbHelper(context);
            }
            return dbHelper;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DatabaseHandler.SQL_CREATE_TABLE_FRIENDS);
            db.execSQL(DatabaseHandler.SQL_CREATE_TABLE_BOOKINGS);
            db.execSQL(DatabaseHandler.SQL_CREATE_TABLE_FRIENDS_IN_BOOKING);
            db.execSQL(DatabaseHandler.SQL_CREATE_TABLE_RESTAURANTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public static void deleteDB(Context context){
            context.deleteDatabase(DATABASE_NAME);
        }
    }

    public static class DataProccessing{


        public static ArrayList<Restaurant> getRestaurants(Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<Restaurant> arrayList = new ArrayList<>();
            Restaurant restaurant = new Restaurant();

            String[] projection = {RestaurantsTable._ID,
                    RestaurantsTable.COLUMN_NAME_NAME,
                    RestaurantsTable.COLUMN_NAME_ADDRESS,
                    RestaurantsTable.COLUMN_NAME_PHONE,
                    RestaurantsTable.COLUMN_NAME_TYPE
            };

            Cursor cursor = db.query(
                    RestaurantsTable.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null

            );

            if(cursor.moveToFirst()){
                restaurant.set_ID(cursor.getInt(cursor.getColumnIndex(RestaurantsTable._ID)));
                restaurant.setName(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_NAME)));
                restaurant.setAddress(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_ADDRESS)));
                restaurant.setPhone(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_PHONE)));
                restaurant.setType(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_TYPE)));
                arrayList.add(restaurant);
                while (cursor.moveToNext()){
                    restaurant = new Restaurant();
                    restaurant.set_ID(cursor.getInt(cursor.getColumnIndex(RestaurantsTable._ID)));
                    restaurant.setName(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_NAME)));
                    restaurant.setAddress(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_ADDRESS)));
                    restaurant.setPhone(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_PHONE)));
                    restaurant.setType(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_TYPE)));
                    arrayList.add(restaurant);
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;
        }

        public static ArrayList<String> getRestaurantsNames(Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<String> arrayList = new ArrayList<>();

            String[] projection = {RestaurantsTable.COLUMN_NAME_NAME};

            Cursor cursor = db.query(
                    RestaurantsTable.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null

            );

            if(cursor.moveToFirst()){
                arrayList.add(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_NAME)));
                while (cursor.moveToNext()){
                    arrayList.add(cursor.getString(cursor.getColumnIndex(RestaurantsTable.COLUMN_NAME_NAME)));
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;
        }

        public static ArrayList<Friend> getFriends(Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<Friend> arrayList = new ArrayList<>();
            Friend friend = new Friend();

            String[] projection = {FriendsTable._ID,
                    FriendsTable.COLUMN_NAME_NAME,
                    FriendsTable.COLUMN_NAME_PHONE
            };

            Cursor cursor = db.query(
                    FriendsTable.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()){
                friend.set_ID(cursor.getInt(cursor.getColumnIndex(FriendsTable._ID)));
                friend.setName(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                friend.setPhone(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_PHONE)));
                arrayList.add(friend);
                while (cursor.moveToNext()){
                    friend = new Friend();
                    friend.set_ID(cursor.getInt(cursor.getColumnIndex(FriendsTable._ID)));
                    friend.setName(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                    friend.setPhone(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_PHONE)));
                    arrayList.add(friend);
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;

        }

        public static ArrayList<String> getFriendsNames(Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<String> arrayList = new ArrayList<>();

            String[] projection = {FriendsTable.COLUMN_NAME_NAME};

            Cursor cursor = db.query(
                    FriendsTable.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null

            );

            if(cursor.moveToFirst()){
                arrayList.add(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                while (cursor.moveToNext()){
                    arrayList.add(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;
        }

        public static ArrayList<Booking> getBookings(Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<Booking> arrayList = new ArrayList<>();
            Booking booking = new Booking();

            String[] projection = {BookingsTable._ID,
                    BookingsTable.COLUMN_NAME_RESTAURANT,
                    BookingsTable.COLUMN_NAME_DATE,
                    BookingsTable.COLUMN_NAME_TIME
            };

            Cursor cursor = db.query(
                    BookingsTable.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()){
                booking.set_ID(cursor.getInt(cursor.getColumnIndex("_ID")));
                booking.setRestaurant(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_RESTAURANT)));
                //booking.setFriends(getFriendsInBooking(booking.get_ID()));
                booking.setDate(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_DATE)));
                booking.setDate(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_DATE)));
                arrayList.add(booking);
                while (cursor.moveToNext()){
                    booking.set_ID(cursor.getInt(cursor.getColumnIndex("_ID")));
                    booking.setRestaurant(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_RESTAURANT)));
                    //booking.setFriends(getFriendsInBooking(booking.get_ID()));
                    booking.setDate(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_DATE)));
                    booking.setDate(cursor.getString(cursor.getColumnIndex(BookingsTable.COLUMN_NAME_DATE)));
                    arrayList.add(booking);
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;

        }

        public static ArrayList<Friend> getFriendsInBooking(int _ID,Context context){
            SQLiteDatabase db =  DbHelper.getInstance(context).getReadableDatabase();
            ArrayList<Friend> arrayList = new ArrayList<>();
            Friend friend = new Friend();
            String[] args = new String[]{Integer.toString(_ID)};

            String[] projection = {FriendsTable._ID,
                    FriendsTable.COLUMN_NAME_NAME,
                    FriendsTable.COLUMN_NAME_PHONE
            };

            Cursor cursor = db.rawQuery(SQL_SELECT_ALL_FRIENDS_IN_BOOKING,args);

            if (cursor.moveToFirst()){
                friend.set_ID(cursor.getInt(cursor.getColumnIndex("_ID")));
                friend.setName(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                friend.setPhone(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_PHONE)));
                arrayList.add(friend);
                while (cursor.moveToNext()){
                    friend.set_ID(cursor.getInt(cursor.getColumnIndex("_ID")));
                    friend.setName(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_NAME)));
                    friend.setPhone(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_NAME_PHONE)));
                    arrayList.add(friend);
                }
                db.close();
                return arrayList;
            }
            db.close();
            return arrayList;

        }

        public static boolean insertRestaurant(Restaurant restaurant,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(RestaurantsTable.COLUMN_NAME_NAME,restaurant.getName());
            values.put(RestaurantsTable.COLUMN_NAME_ADDRESS,restaurant.getAddress());
            values.put(RestaurantsTable.COLUMN_NAME_PHONE,restaurant.getPhone());
            values.put(RestaurantsTable.COLUMN_NAME_TYPE,restaurant.getType());

            if(db.insert(RestaurantsTable.TABLE_NAME,null,values) != -1){
                db.close();
                return true;
            }
            db.close();
            return false;
        }

        public static boolean insertFriend(Friend friend,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(FriendsTable.COLUMN_NAME_NAME,friend.getName());
            values.put(FriendsTable.COLUMN_NAME_PHONE,friend.getPhone());

            if(db.insert(FriendsTable.TABLE_NAME,null,values) != -1){
                db.close();
                return true;
            }
            db.close();
            return false;
        }

        public static boolean insertBooking(Booking booking,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            Iterator iterator = booking.getFriends().listIterator();

            db.beginTransaction();

            values.put(BookingsTable.COLUMN_NAME_RESTAURANT,booking.getRestaurant());
            values.put(BookingsTable.COLUMN_NAME_DATE,booking.getDate());
            values.put(BookingsTable.COLUMN_NAME_TIME,booking.getDate());
            if(db.insert(BookingsTable.TABLE_NAME,null,values ) == -1 ){
                db.endTransaction();
                db.close();
                return false;
            }

            while (iterator.hasNext()){
                values = new ContentValues();
                Friend friend = (Friend) iterator.next();
                values.put(FriendsInBookingTable.COLUMN_NAME_FRIEND_ID,friend.get_ID());
                values.put(FriendsInBookingTable.COLUMN_NAME_BOOKING_ID,booking.get_ID());
                if(db.insert(FriendsInBookingTable.TABLE_NAME,null,values) == -1){
                    db.endTransaction();
                    db.close();
                    return false;
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;

        }

        public static boolean updateRestaurant(Restaurant restaurant,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            String whereClause = RestaurantsTable._ID + " == ?";
            String[]args = {Integer.toString(restaurant.get_ID())};
            values.put(RestaurantsTable.COLUMN_NAME_NAME,restaurant.getName());
            values.put(RestaurantsTable.COLUMN_NAME_ADDRESS,restaurant.getAddress());
            values.put(RestaurantsTable.COLUMN_NAME_PHONE,restaurant.getPhone());
            values.put(RestaurantsTable.COLUMN_NAME_TYPE,restaurant.getType());

            if(db.update(RestaurantsTable.TABLE_NAME,values,whereClause,args) > 0){
                db.close();
                return true;
            }

            db.close();
            return false;
        }

        public static boolean updateFriend(Friend friend,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            String whereClause = FriendsTable._ID + " == ?";
            String[]args = {Integer.toString(friend.get_ID())};
            values.put(FriendsTable.COLUMN_NAME_NAME,friend.getName());
            values.put(FriendsTable.COLUMN_NAME_PHONE,friend.getPhone());

            if(db.update(FriendsTable.TABLE_NAME,values,whereClause,args) > 0){
                db.close();
                return true;
            }

            db.close();
            return false;
        }

        public static boolean deleteRestaurant(Restaurant restaurant,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            String whereClause = RestaurantsTable._ID + " == ?";
            String[]args = {Integer.toString(restaurant.get_ID())};

            if(db.delete(RestaurantsTable.TABLE_NAME,whereClause,args) > 0){
                db.close();
                return true;
            }

            db.close();
            return false;
        }

        public static boolean deleteFriend(Friend friend,Context context){
            SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
            String whereClause = FriendsTable._ID + " == ?";
            String[]args = {Integer.toString(friend.get_ID())};

            if(db.delete(FriendsTable.TABLE_NAME,whereClause,args) > 0){
                db.close();
                return true;
            }

            db.close();
            return false;
        }
    }
}
