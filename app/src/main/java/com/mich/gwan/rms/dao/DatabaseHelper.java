/*
 * Copyright (c) 2023. Maina Michael.
 */

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
    private static final String TABLE_TENANT_ROOM = "tenant_room";

    // Table Manager Column Names
    private static final String COLUMN_MANAGER_ID = "manager_id";
    private static final String COLUMN_MANAGER_FIRSTNAME = "manager_fname";
    private static final String COLUMN_MANAGER_MIDDLE_NAME = "manager_midname";
    private static final String COLUMN_MANAGER_LASTNAME = "manager_lname";
    private static final String COLUMN_MANAGER_PHONE = "manager_phone";
    private static final String COLUMN_MANAGER_EMAIL = "manager_email";
    private static final String COLUMN_MANAGER_LOCATION = "manager_location";
    private static final String COLUMN_MANAGER_PASSWORD = "manager_password";

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
    private static final String COLUMN_PAYMENT_RENT_INVOICE = "payment_rent_invoice";
    private static final String COLUMN_PAYMENT_GARBAGE_INVOICE = "payment_garbage_invoice";

    /**
     * ---------------------------------------------------------------------------------------------
     * SQL Queries
     * These is a list of all queries in this program for CREATE TABLE
     * ---------------------------------------------------------------------------------------------
     */

    // create table manager sql query
    private final String CREATE_TABLE_MANAGER = "CREATE TABLE " + TABLE_MANAGER + "("
            + COLUMN_MANAGER_ID + " INTEGER PRIMARY KEY UNIQUE NOT NULL,"
            + COLUMN_MANAGER_FIRSTNAME + " TEXT NOT NULL,"
            + COLUMN_MANAGER_MIDDLE_NAME + " TEXT,"
            + COLUMN_MANAGER_LASTNAME + " TEXT NOT NULL,"
            + COLUMN_MANAGER_PHONE + " TEXT UNIQUE NOT NULL,"
            + COLUMN_MANAGER_EMAIL + " TEXT UNIQUE NOT NULL,"
            + COLUMN_MANAGER_LOCATION + " TEXT,"
            + COLUMN_MANAGER_PASSWORD + " TEXT UNIQUE NOT NULL" + ")";
    // create table manager sql query
    private final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENTS + "("
            + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_CLIENT_FIRSTNAME + " TEXT NOT NULL,"
            + COLUMN_CLIENT_MIDDLE_NAME + " TEXT,"
            + COLUMN_CLIENT_LASTNAME + " TEXT NOT NULL,"
            + COLUMN_CLIENT_LOCATION + " TEXT,"
            + COLUMN_CLIENT_PHONE + " TEXT UNIQUE NOT NULL,"
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
            + COLUMN_TENANT_FIRSTNAME + " TEXT NOT NULL,"
            + COLUMN_TENANT_MIDDLE_NAME + " TEXT,"
            + COLUMN_TENANT_LASTNAME + " TEXT NOT NULL,"
            + COLUMN_TENANT_PHONE + " TEXT,"
            + COLUMN_TENANT_EMAIL + " TEXT UNIQUE NOT NULL,"
            + COLUMN_TENANT_ROOM_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_TENANT_ROOM_ID+ ") REFERENCES " +TABLE_ROOMS+ "(" +COLUMN_ROOM_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
    // create table tenant sql query
    private final String CREATE_TABLE_PAYMENTS = "CREATE TABLE " + TABLE_PAYMENTS + "("
            + COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_PAYMENT_DATE + " TEXT NOT NULL,"
            + COLUMN_PAYMENT_AMOUNT + " TEXT,"
            + COLUMN_PAYMENT_UNPAID + " TEXT,"
            + COLUMN_PAYMENT_GARBAGE + " TEXT,"
            + COLUMN_PAYMENT_MONTH + " TEXT,"
            + COLUMN_PAYMENT_RENT_INVOICE + " TEXT,"
            + COLUMN_PAYMENT_GARBAGE_INVOICE + " TEXT,"
            + COLUMN_PAYMENT_TENANT_ID + " TEXT,"
            + "FOREIGN KEY(" +COLUMN_PAYMENT_TENANT_ID+ ") REFERENCES " +TABLE_TENANTS+ "(" +COLUMN_TENANT_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";

    // create table tenant room
    private final String CREATE_TABLE_ROOM_TENANT = "CREATE TABLE " + TABLE_TENANT_ROOM + "("
            + COLUMN_TENANT_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
            + COLUMN_TENANT_ID + " TEXT NOT NULL,"
            + COLUMN_ROOM_ID + " TEXT NOT NULL,"
            + "FOREIGN KEY(" +COLUMN_TENANT_ID+ ") REFERENCES " +TABLE_TENANTS+ "(" +COLUMN_TENANT_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE,"
            + "FOREIGN KEY(" +COLUMN_ROOM_ID+ ") REFERENCES " +TABLE_ROOMS+ "(" +COLUMN_ROOM_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";


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
     * creates the database once the apk has been launched
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
        db.execSQL(CREATE_TABLE_ROOM_TENANT);
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
     * Overridden method from super class for db version downgrade
     * @param db instance of the SQlite db
     * @param oldVersion current db version
     * @param newVersion new db version
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Inserts a new manager record
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
        values.put(COLUMN_MANAGER_PASSWORD, par.getPassword());

        //Insert Row
        db.insert(TABLE_MANAGER, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Inserts a new client record
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
     * Inserts a new house record
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

    public void addTenantRoom(String roomId, String tenantId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENANT_ROOM_ID, "");
        values.put(COLUMN_ROOM_ID, roomId);
        values.put(COLUMN_TENANT_ID, tenantId);

        //Insert Row
        db.insert(CREATE_TABLE_ROOM_TENANT, null, values);
        db.close();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Inserts a new room record
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
     * Inserts a new tenant record
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
     * Inserts a new payment record
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
        values.put(COLUMN_PAYMENT_RENT_INVOICE, par.getRentInvoice());
        values.put(COLUMN_PAYMENT_GARBAGE_INVOICE, par.getGarbageInvoice());
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
                obj.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_MANAGER_PASSWORD)));
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
        String[] columns = {"*"};
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
                obj.setRentInvoice(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_RENT_INVOICE)));
                obj.setGarbageInvoice(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_GARBAGE_INVOICE)));
                obj.setTenantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_TENANT_ID)));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Checks if user exists
     * @param idNumber entered id number
     * @param password entered password
     * @return true if exist
     */
    public boolean checkUser(String idNumber, String password) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_MANAGER_ID + " = ? AND "+ COLUMN_MANAGER_PASSWORD + " = ?";
        String[] selectionArgs = {idNumber, password};
        Cursor cursor = db.query(
                TABLE_MANAGER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if user exists using id number and email
     * @param idNumber provided id number
     * @param email provided email
     * @return true if exists
     */
    public boolean checkUser(int idNumber, String email) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_MANAGER_ID + " = ? AND "+ COLUMN_MANAGER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(idNumber), email};
        Cursor cursor = db.query(
                TABLE_MANAGER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks for any entry of a user
     * @return true if any record is found
     */
    public boolean checkUser() {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_MANAGER, columns, null, null, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if the client already exists
     * @param firstName client first name
     * @param surname client surname
     * @return true if exists
     */
    public boolean checkClient(String firstName, String surname) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CLIENT_FIRSTNAME+ " = ? AND "+ COLUMN_CLIENT_LASTNAME + " = ?";
        String[] selectionArgs = {String.valueOf(firstName), surname};
        Cursor cursor = db.query(
                TABLE_CLIENTS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if a tenant exists with the given parameters
     * @param phone phone number of the tenant
     * @param email email of the tenant
     * @return true if tenant exists
     */
    public boolean checkTenant(String phone, String email) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_TENANT_PHONE+ " = ? AND "+ COLUMN_TENANT_EMAIL + " = ?";
        String[] selectionArgs = {phone, email};
        Cursor cursor = db.query(
                TABLE_TENANTS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if house already exists
     * @param houseName name of the house
     * @param clientId the client id
     * @return
     */
    public boolean checkHouse(String houseName, String clientId) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_HOUSE_NAME+ " = ? AND "+ COLUMN_HOUSE_CLIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(houseName), clientId};
        Cursor cursor = db.query(
                TABLE_HOUSES, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    public boolean checkRoom(String houseId, String roomId) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_ROOM_HOUSE_ID+ " = ? AND "+ COLUMN_ROOM_ID + " = ?";
        String[] selectionArgs = {String.valueOf(houseId), roomId};
        Cursor cursor = db.query(
                TABLE_ROOMS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if user phone is unique
     * @param phone
     * @return
     */
    public boolean checkClientPhoneUnique(String phone) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CLIENT_PHONE+ " = ?";
        String[] selectionArgs = {phone};
        Cursor cursor = db.query(
                TABLE_CLIENTS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Checks if there exists any other instance of the given
     * phone number in tenants table.
     * @param phone the provided phone number.
     * @return true if exists.
     */
    public boolean checkTenantPhoneUnique(String phone) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_TENANT_PHONE+ " = ?";
        String[] selectionArgs = {phone};
        Cursor cursor = db.query(
                TABLE_TENANTS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * checks if tenant is assigned a room
     * @param tenantId tenant id
     * @return true
     */
    public boolean checkClientIsAssignedRoom(String tenantId) {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CLIENT_ID+ " = ?";
        String[] selectionArgs = {tenantId};
        Cursor cursor = db.query(
                TABLE_TENANT_ROOM, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * updates client data
     * @param par client object
     */
    public void updateClient(Client par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_FIRSTNAME, par.getFirstName());
        values.put(COLUMN_CLIENT_MIDDLE_NAME, par.getSecondName());
        values.put(COLUMN_CLIENT_LASTNAME, par.getLastName());
        values.put(COLUMN_CLIENT_LOCATION, par.getLocation());
        values.put(COLUMN_CLIENT_PHONE, par.getPhoneNumber());
        values.put(COLUMN_CLIENT_MANAGER_ID, par.getManagerId());

        // updating row
        db.update(TABLE_CLIENTS, values, COLUMN_CLIENT_ID + " = ?", new String[]{String.valueOf(par.getClientId())});
        db.close();
    }

    /**
     * updates house data
     * @param par house object
     */
    public void updateHouse(House par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUSE_NAME, par.getHouseName());
        values.put(COLUMN_HOUSE_LOCATION, par.getLocation());
        values.put(COLUMN_HOUSE_TYPE, par.getHouseType());
        values.put(COLUMN_HOUSE_CLIENT_ID, par.getClientId());

        // updating row
        db.update(TABLE_HOUSES, values, COLUMN_HOUSE_ID + " = ?", new String[]{String.valueOf(par.getHouseId())});
        db.close();
    }

    /**
     * updates room data
     * @param par room object
     */
    public void updateRoom(Room par) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TYPE, par.getRoomType());
        values.put(COLUMN_ROOM_RENT, par.getRent());
        values.put(COLUMN_ROOM_DEPOSIT, par.getDeposit());
        values.put(COLUMN_ROOM_HOUSE_ID, par.getHouseId());

        // updating row
        db.update(TABLE_ROOMS, values, COLUMN_ROOM_ID + " = ?", new String[]{String.valueOf(par.getRoomId())});
        db.close();
    }

    /**
     * Deletes the given client data
     * @param obj client object
     */
    public void deleteClient(Client obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete record by id
        db.delete(TABLE_CLIENTS, COLUMN_CLIENT_ID + " = ?",
                new String[]{String.valueOf(obj.getClientId())});
        db.close();
    }

    /**
     *  Deletes house data
     * @param obj house object
     */
    public void deleteHouse(House obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete record by id
        db.delete(TABLE_HOUSES, COLUMN_HOUSE_ID + " = ?",
                new String[]{String.valueOf(obj.getHouseId())});
        db.close();
    }

    /**
     * Deletes a row data from the room table
     * @param obj room parameters
     */
    public void deleteRoom(Room obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete record by id
        db.delete(TABLE_ROOMS, COLUMN_ROOM_ID + " = ?",
                new String[]{String.valueOf(obj.getRoomId())});
        db.close();
    }

    /**
     * Gets the total number of registered clients
     * @return total registered clients
     */
    public int clientCount() {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(
                TABLE_CLIENTS, columns, null, null, null, null, null);
        return cursor.getCount();
    }

    /**
     * Gets the total number of registered houses
     * @return registered houses count
     */
    public int houseCount() {
        String[] columns = {"*"};
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(
                TABLE_HOUSES, columns, null, null, null, null, null);
        return cursor.getCount();
    }

    /**
     * Checks whether the room is occupied or vacant
     * @param houseId the house id of the room being checked
     * @return room status
     */
    @SuppressLint("Range")
    public String getRoomStatus(int houseId, int roomId) {
        String[] columns = {"*"};
        String selection =COLUMN_ROOM_HOUSE_ID + " = ? AND " + COLUMN_ROOM_ID + " = ?";
        String[] selectionArgs = {String.valueOf(houseId), String.valueOf(roomId)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_ROOMS, columns, selection, selectionArgs, null, null, null);
        // return column value
        return cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_STATUS));
    }


    /**
     * Raw Query for joining Tables
     *
    public int getAllSpareSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_TOTAL + ") as Total FROM " + TABLE_SPARE_SALES, null);
        cursor = db.rawQuery();
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        return total;
    }**/

}
