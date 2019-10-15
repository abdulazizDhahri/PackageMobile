package com.mhdhussein.aralpackagemanagement.api;

import com.mhdhussein.aralpackagemanagement.model.Attendance;
import com.mhdhussein.aralpackagemanagement.model.Courier;
import com.mhdhussein.aralpackagemanagement.model.CourierAttendanceData;
import com.mhdhussein.aralpackagemanagement.model.MainReportData;
import com.mhdhussein.aralpackagemanagement.model.ShipmentEntryData;
import com.mhdhussein.aralpackagemanagement.model.SummaryData;
import com.mhdhussein.aralpackagemanagement.model.Token;
import com.mhdhussein.aralpackagemanagement.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @POST("oauth/token")
    @FormUrlEncoded
    Observable<Token> login(
            @Field("username") int idNumber ,
            @Field("password") String password,
            @Field("grant_type") String grantType,
            @Field("client_secret") String clientSecret,
            @Field("client_id") int clientId
            );

    @POST("api/v1/current-user")
    Observable<User> currentUser(@Header("Authorization") String authToken);



    @POST("api/v1/package/supervisor_reports")
    @FormUrlEncoded
    Observable<SummaryData> getSummary(@Header("Authorization") String authToken,
                                       @Field("id") int id ,
                                       @Field("type") int type);

    @POST("api/v1/package/supervisor")
    @FormUrlEncoded
    Observable<MainReportData> getMainReport(@Header("Authorization") String authToken,
                                             @Field("from") String fromDate,
                                             @Field("to") String toDate,
                                             @Field("id") int id
                                          );

    @GET("api/v1/couriers_app/{id}")
    Observable<Courier> getCourier(@Header("Authorization") String authToken,
                                   @Path("id") String id
                                   );

    @POST("api/v1/attendances")
    @FormUrlEncoded
    Observable<Attendance> createAttendance(
            @Header("Authorization") String authToken,
            @Field("courier_id") int id,
            @Field("supervisor_id") int supervisorId
    );

    @POST("api/v1/attended")
    @FormUrlEncoded
    Observable<Integer> isAttended(
            @Header("Authorization") String authToken,
            @Field("courier_id") int id
    );

    @POST("api/v1/package/supervisor_courier_count")
    @FormUrlEncoded
    Observable<Integer> getCourierCount(
            @Header("Authorization") String authToken,
            @Field("id") int id
    );


    @POST("api/v1/attended_count")
    @FormUrlEncoded
    Observable<Integer> getAttendanceCount(
            @Header("Authorization") String authToken,
            @Field("supervisor_id") int supervisorId
    );

    @POST("api/v1/attended_couriers")
    @FormUrlEncoded
    Observable<CourierAttendanceData> getAttendedCourier(
            @Header("Authorization") String authToken,
            @Field("supervisor_id") int supervisorId
    );

    @POST("api/v1/shipment_exists")
    @FormUrlEncoded
    Observable<Integer> isShipmentExists(
            @Header("Authorization") String authToken,
            @Field("courier_id") int id
    );


    @POST("api/v1/shipments")
    @FormUrlEncoded
    Observable<ShipmentEntryData> insertShipment(
            @Header("Authorization") String authToken,
            @Field("courier_id") int courierId,
            @Field("supervisor_id") int supervisorId,
            @Field("sent_count") int ofd,
            @Field("sent_amount") double packageValue
    );

    @PATCH("api/v1/shipments/{id}")
    @FormUrlEncoded
    Observable<ShipmentEntryData> insertShipmentEnd(
            @Header("Authorization") String authToken,
            @Path("id") int id,
            @Field("courier_id") int courierId,
            @Field("supervisor_id") int supervisorId,
            @Field("sent_count") double ofd,
            @Field("sent_amount") double packageValue,
            @Field("cash") double cash,
            @Field("cash_packages") double cashPackages,
            @Field("credit") double credit,
            @Field("credit_packages") double creditPackages,
            @Field("deposited_amount") double deposited,
            @Field("returned_shipments") double returned,
            @Field("delivered_shipments") double delivered,
            @Field("fiscal_deficit") double deficit,
            @Field("lost_shipments") double lost,
            @Field("fuel") double fuel
    );


    @POST("api/v1/courier_shipment")
    @FormUrlEncoded
    Observable<ShipmentEntryData> getShipment(
            @Header("Authorization") String authToken,
            @Field("courier_id") int courierId

    );





}
