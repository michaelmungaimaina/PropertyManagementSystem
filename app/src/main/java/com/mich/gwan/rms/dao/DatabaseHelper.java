package com.mich.gwan.rms.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mich.gwan.rms.models.Client;
import com.mich.gwan.rms.models.House;
import com.mich.gwan.rms.models.Manager;
import com.mich.gwan.rms.models.Payment;
import com.mich.gwan.rms.models.Room;
import com.mich.gwan.rms.models.Tenant;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "propertymgt.db";

    // Table names
    private static final String TABLE_MANAGER = "managers";
    private static final String TABLE_TENANTS = "tenants";
    private static final String TABLE_HOUSES = "houses";
    private static final String TABLE_ROOMS = "rooms";
    private static final String TABLE_PAYMENTS = "payments";
    private static final String TABLE_CLIENTS = "clients";

    // Table Manager Column Names
    private static final String COLUMN_MANAGER_ID = "manager_id";
    private static final String COLUMN_MANAGER_FIRSTNAME = "manager_fname";
    private static final String COLUMN_MANAGER_MIDDLE_NAME = "manager_midname";
    private static final String COLUMN_MANAGER_LASTNAME = "manager_lname";
    private static final String COLUMN_MANAGER_PHONE = "manager_phone";
    private static final String COLUMN_MANAGER_EMAIL = "manager_email";
    private static final String COLUMN_MANAGER_LOCATION = "manager_location";

    // Table Client Column Names
    private static final String COLUMN_CLIENT_ID = "client_id";
    private static final String COLUMN_CLIENT_FIRSTNAME = "client_fname";
    private static final String COLUMN_CLIENT_MIDDLE_NAME = "client_midname";
    private static final String COLUMN_CLIENT_LASTNAME = "client_lname";
    private static final String COLUMN_CLIENT_LOCATION = "client_location";
    private static final String COLUMN_CLIENT_PHONE = "client_phone";
    private static final String COLUMN_CLIENT_MANAGER_ID = "client_managerid";

    // Table House Column Names
    private static final String COLUMN_HOUSE_ID = "house_id";
    private static final String COLUMN_HOUSE_NAME = "house_name";
    private static final String COLUMN_HOUSE_LOCATION = "house_location";
    private static final String COLUMN_HOUSE_TYPE = "house_type";
    private static final String COLUMN_HOUSE_CLIENT_ID = "house_clientid";

    // Table Room Column Names
    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_ROOM_TYPE = "room_type";
    private static final String COLUMN_ROOM_DEPOSIT = "room_deposit";
    private static final String COLUMN_ROOM_RENT = "room_rent";
    private static final String COLUMN_ROOM_STATUS = "room_status";
    private static final String COLUMN_ROOM_HOUSE_ID = "room_houseid";

    // Table Tenant Column Names
    private static final String COLUMN_TENANT_ID = "tenant_id";
    private static final String COLUMN_TENANT_FIRSTNAME = "tenant_fname";
    private static final String COLUMN_TENANT_MIDDLE_NAME = "tenant_midname";
    private static final String COLUMN_TENANT_LASTNAME = "tenant_lname";
    private static final String COLUMN_TENANT_PHONE = "tenant_phone";
    private static final String COLUMN_TENANT_EMAIL = "tenant_email";
    private static final String COLUMN_TENANT_ROOM_ID = "tenant_roomid";

    // Table Payment Column Names
    private static final String COLUMN_PAYMENT_ID = "payment_id";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";
    private static final String COLUMN_PAYMENT_AMOUNT = "payment_amountpaid";
    private static final String COLUMN_PAYMENT_MONTH = "payment_month";
    private static final String COLUMN_PAYMENT_UNPAID = "payment_unpaid";
    private static final String COLUMN_PAYMENT_GARBAGE = "payment_garbagefee";
    private static final String COLUMN_PAYMENT_TENANT_ID = "payment_tenantid";

    /**
     * ---------------------------------------------------------------------------------------------
     * SQL Queries
     * These is a list of all queries in this program for CREATE TABLE
     * ---------------------------------------------------------------------------------------------
     */

    // create table manager sql query
    private final String CREATE_TABLE_MANAGER = "CREATE TABLE " + TABLE_MANAGER + "("
            + COLUMN_MANAGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_MANAGER_FIRSTNAME + " TEXT,"
            + COLUMN_MANAGER_MIDDLE_NAME + " TEXT,"
            + COLUMN_MANAGER_LASTNAME + " TEXT,"
            + COLUMN_MANAGER_PHONE + " TEXT,"
            + COLUMN_MANAGER_EMAIL + " TEXT,"
            + COLUMN_MANAGER_LOCATION + " TEXT" + ")";
    // create table manager sql query
    private final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENTS + "("
            + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_CLIENT_FIRSTNAME + " TEXT,"
            + COLUMN_CLIENT_MIDDLE_NAME + " TEXT,"
            + COLUMN_CLIENT_LASTNAME + " TEXT,"
            + COLUMN_CLIENT_LOCATION + " TEXT,"
            + COLUMN_CLIENT_PHONE + " TEXT,"
            + COLUMN_CLIENT_MANAGER_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_CLIENT_MANAGER_ID+ ") REFERENCES " +TABLE_MANAGER+ "(" +COLUMN_MANAGER_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
    // create table house sql query
    private final String CREATE_TABLE_HOUSE = "CREATE TABLE " + TABLE_HOUSES + "("
            + COLUMN_HOUSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_HOUSE_NAME + " TEXT,"
            + COLUMN_HOUSE_LOCATION + " TEXT,"
            + COLUMN_HOUSE_TYPE + " TEXT,"
            + COLUMN_HOUSE_CLIENT_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_HOUSE_CLIENT_ID+ ") REFERENCES " +TABLE_CLIENTS+ "(" +COLUMN_CLIENT_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
    // create table room sql query
    private final String CREATE_TABLE_ROOM = "CREATE TABLE " + TABLE_ROOMS + "("
            + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_ROOM_TYPE + " TEXT,"
            + COLUMN_ROOM_DEPOSIT + " TEXT,"
            + COLUMN_ROOM_RENT + " TEXT,"
            + COLUMN_ROOM_STATUS + " TEXT,"
            + COLUMN_ROOM_HOUSE_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_ROOM_HOUSE_ID+ ") REFERENCES " +TABLE_HOUSES+ "(" +COLUMN_HOUSE_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
    // create table tenant sql query
    private final String CREATE_TABLE_TENANT = "CREATE TABLE " + TABLE_TENANTS + "("
            + COLUMN_TENANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_TENANT_FIRSTNAME + " TEXT,"
            + COLUMN_TENANT_MIDDLE_NAME + " TEXT,"
            + COLUMN_TENANT_LASTNAME + " TEXT,"
            + COLUMN_TENANT_PHONE + " TEXT,"
            + COLUMN_TENANT_EMAIL + " TEXT,"
            + COLUMN_TENANT_ROOM_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_TENANT_ROOM_ID+ ") REFERENCES " +TABLE_ROOMS+ "(" +COLUMN_ROOM_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
    // create table tenant sql query
    private final String CREATE_TABLE_PAYMENTS = "CREATE TABLE " + TABLE_PAYMENTS + "("
            + COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_PAYMENT_DATE + " TEXT,"
            + COLUMN_PAYMENT_AMOUNT + " TEXT,"
            + COLUMN_PAYMENT_UNPAID + " TEXT,"
            + COLUMN_PAYMENT_GARBAGE + " TEXT,"
            + COLUMN_PAYMENT_MONTH + " TEXT,"
            + COLUMN_PAYMENT_TENANT_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_PAYMENT_TENANT_ID+ ") REFERENCES " +TABLE_TENANTS+ "(" +COLUMN_TENANT_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";


    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor for databaseHelper
     * It's used to switch database based on the context parameter
     * @param context open SQLite database
     * ---------------------------------------------------------------------------------------------
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Method for creating the database oce the apk has been launched
     * @param db SQLite database
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MANAGER);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_HOUSE);
        db.execSQL(CREATE_TABLE_ROOM);
        db.execSQL(CREATE_TABLE_TENANT);
        db.execSQL(CREATE_TABLE_PAYMENTS);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Method for applying changes to db on upgrade
     * newVersion is greater than oldVersion
     * @param db SQLite database
     * @param oldVersion current db version
     * @param newVersion next db version
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Method for applying changes to db on downgrade
     * newVersion less than oldVersion
     * @param db SQLite database
     * @param oldVersion current db version
     * @param newVersion next db version
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new manager record
     * @param par manager model
     * ---------------------------------------------------------------------------------------------
     */
    public void addManager(Manager par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MANAGER_ID, par.getManagerId());
        values.put(COLUMN_MANAGER_FIRSTNAME, par.getFirstName());
        values.put(COLUMN_MANAGER_MIDDLE_NAME, par.getSecondName());
        values.put(COLUMN_MANAGER_LASTNAME, par.getLastName());
        values.put(COLUMN_MANAGER_PHONE, par.getPhoneNumber());
        values.put(COLUMN_MANAGER_EMAIL, par.getEmail());
        values.put(COLUMN_MANAGER_LOCATION, par.getLocation());

        //Insert Row
        db.insert(TABLE_MANAGER, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new client record
     * @param par client model
     * ---------------------------------------------------------------------------------------------
     */
    public void addClient(Client par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_ID, par.getClientId());
        values.put(COLUMN_CLIENT_FIRSTNAME, par.getFirstName());
        values.put(COLUMN_CLIENT_MIDDLE_NAME, par.getSecondName());
        values.put(COLUMN_CLIENT_LASTNAME, par.getLastName());
        values.put(COLUMN_CLIENT_LOCATION, par.getLocation());
        values.put(COLUMN_CLIENT_PHONE, par.getPhoneNumber());
        values.put(COLUMN_CLIENT_MANAGER_ID, par.getManagerId());

        //Insert Row
        db.insert(TABLE_CLIENTS, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new house record
     * @param par house model
     * ---------------------------------------------------------------------------------------------
     */
    public void addHouse(House par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_ID, par.getHouseId());
        values.put(COLUMN_HOUSE_NAME, par.getHouseName());
        values.put(COLUMN_HOUSE_LOCATION, par.getLocation());
        values.put(COLUMN_HOUSE_TYPE, par.getHouseType());
        values.put(COLUMN_HOUSE_CLIENT_ID, par.getClientId());

        //Insert Row
        db.insert(TABLE_HOUSES, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new room record
     * @param par room model
     * ---------------------------------------------------------------------------------------------
     */
    public void addRoom(Room par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_ID, par.getRoomId());
        values.put(COLUMN_ROOM_TYPE, par.getRoomType());
        values.put(COLUMN_ROOM_DEPOSIT, par.getDeposit());
        values.put(COLUMN_ROOM_RENT, par.getRent());
        values.put(COLUMN_ROOM_STATUS, par.getStatus());
        values.put(COLUMN_ROOM_HOUSE_ID, par.getHouseId());

        //Insert Row
        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new tenant record
     * @param par tenant model
     * ---------------------------------------------------------------------------------------------
     */
    public void addTenant(Tenant par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENANT_ID, par.getTenantId());
        values.put(COLUMN_TENANT_FIRSTNAME, par.getTenantFirstName());
        values.put(COLUMN_TENANT_MIDDLE_NAME, par.getTenantSecondName());
        values.put(COLUMN_TENANT_LASTNAME, par.getTenantLastName());
        values.put(COLUMN_TENANT_PHONE, par.getTenantPhoneNumber());
        values.put(COLUMN_TENANT_EMAIL, par.getTenantEmail());
        values.put(COLUMN_TENANT_ROOM_ID, par.getRoomId());

        //Insert Row
        db.insert(TABLE_TENANTS, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Insert new payment record
     * @param par payment model
     * ---------------------------------------------------------------------------------------------
     */
    public void addPayment(Payment par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_ID, par.getPaymentId());
        values.put(COLUMN_PAYMENT_DATE, par.getPaymentDate());
        values.put(COLUMN_PAYMENT_AMOUNT, par.getPaidAmount());
        values.put(COLUMN_PAYMENT_UNPAID, par.getUnpaidAmount());
        values.put(COLUMN_PAYMENT_GARBAGE, par.getGarbageFee());
        values.put(COLUMN_PAYMENT_MONTH, par.getMonth());
        values.put(COLUMN_PAYMENT_TENANT_ID, par.getTenantId());

        //Insert Row
        db.insert(TABLE_PAYMENTS, null, values);
        db.close();
    }

    /**
     * A list of all manager(s) records
     */
    @SuppressLint("Range")
    public List<Manager> getManagers(){
        String[] columns = {
                COLUMN_MANAGER_ID,
                COLUMN_MANAGER_FIRSTNAME,
                COLUMN_MANAGER_MIDDLE_NAME,
                COLUMN_MANAGER_LASTNAME,
                COLUMN_MANAGER_PHONE,
                COLUMN_MANAGER_EMAIL,
                COLUMN_MANAGER_LOCATION
        };
        String sortOder = COLUMN_MANAGER_FIRSTNAME + " ASC";
        List<Manager> list = new ArrayList<Manager>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MANAGER, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Manager obj = new Manager();
                obj.setManagerId(cursor.getInt(cursor.getColumnIndex(COLUMN_MANAGER_ID)));
                obj.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_FIRSTNAME)));
                obj.setSecondName(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_MIDDLE_NAME)));
                obj.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_LASTNAME)));
                obj.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_PHONE)));
                obj.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_EMAIL)));
                obj.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_LOCATION)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * A list of all Client(s) records
     */
    @SuppressLint("Range")
    public List<Client> getClients(){
        String[] columns = {
                COLUMN_CLIENT_ID,
                COLUMN_CLIENT_FIRSTNAME,
                COLUMN_CLIENT_MIDDLE_NAME,
                COLUMN_CLIENT_LASTNAME,
                COLUMN_CLIENT_LOCATION,
                COLUMN_CLIENT_PHONE,
                COLUMN_CLIENT_MANAGER_ID
        };
        String sortOder = COLUMN_CLIENT_ID + " ASC";
        List<Client> list = new ArrayList<Client>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CLIENTS, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Client obj = new Client();
                obj.setClientId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENT_ID)));
                obj.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_FIRSTNAME)));
                obj.setSecondName(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_MIDDLE_NAME)));
                obj.setThirdName(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_LASTNAME)));
                obj.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_LOCATION)));
                obj.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_PHONE)));
                obj.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_MANAGER_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * A list of all House(s) record(s)
     */
    @SuppressLint("Range")
    public List<House> getHouses(){
        String[] columns = {
                COLUMN_HOUSE_ID,
                COLUMN_HOUSE_NAME,
                COLUMN_HOUSE_LOCATION,
                COLUMN_HOUSE_TYPE,
                COLUMN_HOUSE_CLIENT_ID
        };
        String sortOder = COLUMN_HOUSE_ID + " ASC";
        List<House> list = new ArrayList<House>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_HOUSES, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                House obj = new House();
                obj.setHouseId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOUSE_ID)));
                obj.setHouseName(cursor.getString(cursor.getColumnIndex(COLUMN_HOUSE_NAME)));
                obj.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_HOUSE_LOCATION)));
                obj.setHouseType(cursor.getString(cursor.getColumnIndex(COLUMN_HOUSE_TYPE)));
                obj.setClientId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOUSE_CLIENT_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * A list of all Room(s) record(s)
     */
    @SuppressLint("Range")
    public List<Room> getRooms(){
        String[] columns = {
                COLUMN_ROOM_ID,
                COLUMN_ROOM_TYPE,
                COLUMN_ROOM_DEPOSIT,
                COLUMN_ROOM_RENT,
                COLUMN_ROOM_STATUS,
                COLUMN_ROOM_HOUSE_ID
        };
        String sortOder = COLUMN_ROOM_ID + " ASC";
        List<Room> list = new ArrayList<Room>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ROOMS, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Room obj = new Room();
                obj.setRoomId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID)));
                obj.setRoomType(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_TYPE)));
                obj.setDeposit(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_DEPOSIT)));
                obj.setRent(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_RENT)));
                obj.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_STATUS)));
                obj.setHouseId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_HOUSE_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * A list of all Tenant(s) record(s)
     */
    @SuppressLint("Range")
    public List<Tenant> getTenants(){
        String[] columns = {
                COLUMN_TENANT_ID,
                COLUMN_TENANT_FIRSTNAME,
                COLUMN_TENANT_MIDDLE_NAME,
                COLUMN_TENANT_LASTNAME,
                COLUMN_TENANT_PHONE,
                COLUMN_TENANT_EMAIL,
                COLUMN_TENANT_ROOM_ID
        };
        String sortOder = COLUMN_TENANT_ID + " ASC";
        List<Tenant> list = new ArrayList<Tenant>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TENANTS, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Tenant obj = new Tenant();
                obj.setTenantId(cursor.getInt(cursor.getColumnIndex(COLUMN_TENANT_ID)));
                obj.setTenantFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_TENANT_FIRSTNAME)));
                obj.setTenantSecondName(cursor.getString(cursor.getColumnIndex(COLUMN_TENANT_MIDDLE_NAME)));
                obj.setTenantLastName(cursor.getString(cursor.getColumnIndex(COLUMN_TENANT_LASTNAME)));
                obj.setTenantPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_TENANT_PHONE)));
                obj.setTenantEmail(cursor.getString(cursor.getColumnIndex(COLUMN_TENANT_EMAIL)));
                obj.setRoomId(cursor.getInt(cursor.getColumnIndex(COLUMN_TENANT_ROOM_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * A list of all Payment(s) record(s)
     */
    @SuppressLint("Range")
    public List<Payment> getPayments(){
        String[] columns = {
                COLUMN_PAYMENT_ID,
                COLUMN_PAYMENT_DATE,
                COLUMN_PAYMENT_AMOUNT,
                COLUMN_PAYMENT_UNPAID,
                COLUMN_PAYMENT_GARBAGE,
                COLUMN_PAYMENT_MONTH,
                COLUMN_PAYMENT_TENANT_ID
        };
        String sortOder = COLUMN_PAYMENT_ID + " ASC";
        List<Payment> list = new ArrayList<Payment>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PAYMENTS, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Payment obj = new Payment();
                obj.setPaymentId(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_ID)));
                obj.setPaymentDate(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_DATE)));
                obj.setPaidAmount(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_AMOUNT)));
                obj.setUnpaidAmount(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_UNPAID)));
                obj.setGarbageFee(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_GARBAGE)));
                obj.setGarbageFee(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_MONTH)));
                obj.setTenantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_TENANT_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
