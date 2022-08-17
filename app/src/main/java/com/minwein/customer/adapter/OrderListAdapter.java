package com.minwein.customer.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.minwein.customer.R;
import com.minwein.customer.activity.OrderDetailsActivity;
import com.minwein.customer.activity.TrackOrderActivity;
import com.minwein.customer.api.CommonApiCalls;
import com.minwein.customer.api.Urls;
import com.minwein.customer.api_model.AddRestaurantRatingApiResponse;
import com.minwein.customer.api_model.OrderApiResponse;
import com.minwein.customer.api_model.OrderDetailsApiResponse;
import com.minwein.customer.app_interfaces.CommonCallback;
import com.minwein.customer.model.RatingInputModel;
import com.minwein.customer.model.RatingModel;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.MyApplication;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by AMSHEER on 19-12-2017.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {
    private Context mcontext;
    private List<OrderApiResponse.Datum> orderList;
    private List<OrderDetailsApiResponse.Item> orderDetailItems;
    public static ArrayList<RatingModel> model = new ArrayList<>();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri;

    String imagePath1 = "";
    private String deliveryTimeRating;
    private String foodTemperatureRating;
    private String customerServiceRating;
//    private RatingModel ratingItems;

    public OrderListAdapter(Context context, List<OrderApiResponse.Datum> orderList) {
        this.orderList = orderList;
        this.mcontext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, status, price, dateAndtime, orderId, tvRestaurantName, tvStatus, tvOrderID;
        SimpleDraweeView logo;
        ImageView im_addrating, im_vieworder, im_reorder, im_trackorder;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (SimpleDraweeView) itemView.findViewById(R.id.sdvRestaurantLogo);
            price = (TextView) itemView.findViewById(R.id.tvCost);
            status = (TextView) itemView.findViewById(R.id.tvDeliveryStatus);
            dateAndtime = (TextView) itemView.findViewById(R.id.tvDeliveryDateandTime);
            orderId = (TextView) itemView.findViewById(R.id.tvOrderCode);
            im_addrating = (ImageView) itemView.findViewById(R.id.imOrderAddRating);
            im_vieworder = (ImageView) itemView.findViewById(R.id.imOrderView);
            im_reorder = (ImageView) itemView.findViewById(R.id.imOrderReorder);
            im_trackorder = (ImageView) itemView.findViewById(R.id.imOrderTrackOrder);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.tvRestaurantName);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvOrderID = (TextView) itemView.findViewById(R.id.tvOrderID);
            mcontext = itemView.getContext();
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_orders_list, parent, false);
        return new MyViewHolder(itemview);
    }
    String vendorComments ;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrderApiResponse.Datum orderlist = orderList.get(position);
        String itemImage = Urls.BASE_URL_STAGING + orderlist.getVendor_image();
        CommonFunctions.getInstance().loadImageByFresco(holder.logo, itemImage);
        holder.price.setText(SessionManager.getInstance().getCurrencySymbol() + " " + (CommonFunctions.getInstance().roundOffDecimalValue(Double.parseDouble(orderlist.getOrder_total()))));
        String order_status = "";
        holder.tvStatus.setText(LanguageConstants.status);
        holder.tvOrderID.setText(LanguageConstants.orderId);

        if (orderlist.getRating_status().equals("0")) {
            holder.im_addrating.setVisibility(View.GONE);
        } else {
            holder.im_addrating.setVisibility(View.VISIBLE);
        }
        switch (orderlist.getOrder_status()) {
            case "1":
                order_status = LanguageConstants.pending;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.orange));
                holder.im_trackorder.setVisibility(View.GONE);
                holder.im_addrating.setVisibility(View.VISIBLE);
                break;
            case "2":
                order_status = LanguageConstants.accepted;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.lightgreen));
                holder.im_trackorder.setVisibility(View.GONE);
                holder.im_addrating.setVisibility(View.GONE);
                break;
            case "3":
                order_status = LanguageConstants.prepared;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.lightpink));
                holder.im_trackorder.setVisibility(View.GONE);
                holder.im_addrating.setVisibility(View.GONE);
                break;
            case "4":
                order_status = LanguageConstants.ontheway;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.darkpink));
                holder.im_trackorder.setVisibility(View.VISIBLE);
                holder.im_addrating.setVisibility(View.GONE);
                break;
            case "5":
                order_status = LanguageConstants.delivered;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.colorPrimary));
                holder.im_trackorder.setVisibility(View.GONE);
                break;
            default:
                order_status = LanguageConstants.rejected;
                holder.status.setTextColor(MyApplication.context.getResources().getColor(R.color.darkpink));
                holder.im_trackorder.setVisibility(View.GONE);
                holder.im_addrating.setVisibility(View.GONE);
                break;
        }
        holder.status.setText(order_status);
        final OrderApiResponse.Datum orderlists = orderList.get(position);

//        String itemImages = Urls.BASE_URL_STAGING + orderlists.getVendor_image();
//        CommonFunctions.getInstance().loadImageByFresco(holder.logo, itemImages);
//        holder.price.setText(orderlists.getOrder_total());
//        holder.status.setText(orderlists.getOrder_status());
        holder.dateAndtime.setText(CommonFunctions.getInstance().formatDate(orderlists.getOrder_date_time().toString(), "yyyy-MM-dd HH:mm:ss", "MMM dd,yyyy HH:mm a"));
        holder.orderId.setText(orderlists.getOrder_number());
        holder.tvRestaurantName.setText(orderlist.getVendor_name());

        holder.im_addrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mcontext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addratingpopup);
                if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    }
                }
                ImageView ivRestaurantRatingViewclose;
                SimpleDraweeView ivFoodRestaurants;
                final RatingBar rtbFoodDeliveryTimeRating, rtbFoodTemperatureRating, rtbFoodCustomerServiceRating;
                final EditText etFoodComment;
                final TextView tvFoodRestaurantNames, tvFoodDeliveryTime, tvFoodDeliveryTimeRatingValue, tvFoodTemperature, tvFoodCuisine,
                        tvFoodTemperatureRatingValue, tvFoodRateFoodservice, tvFoodCustomerServiceRatingValue, tvFoodComments, tvFoodCustomerService;

                tvFoodRestaurantNames = (TextView) dialog.findViewById(R.id.tvFoodRestaurantNames);

                tvFoodRateFoodservice = (TextView) dialog.findViewById(R.id.tvFoodRateFoodservice);
                tvFoodDeliveryTime = (TextView) dialog.findViewById(R.id.tvFoodDeliveryTime);
                tvFoodDeliveryTimeRatingValue = (TextView) dialog.findViewById(R.id.tvFoodDeliveryTimeRatingValue);
                tvFoodTemperature = (TextView) dialog.findViewById(R.id.tvFoodTemperature);
                tvFoodTemperatureRatingValue = (TextView) dialog.findViewById(R.id.tvFoodTemperatureRatingValue);
                tvFoodCustomerService = (TextView) dialog.findViewById(R.id.tvFoodCustomerService);
                tvFoodCustomerServiceRatingValue = (TextView) dialog.findViewById(R.id.tvFoodCustomerServiceRatingValue);
                tvFoodComments = (TextView) dialog.findViewById(R.id.tvFoodComments);
                tvFoodCuisine = (TextView) dialog.findViewById(R.id.tvFoodCuisine);
                etFoodComment = (EditText) dialog.findViewById(R.id.etFoodComment);

                tvFoodRateFoodservice.setText(LanguageConstants.rateRestaurantService);
                tvFoodDeliveryTime.setText(LanguageConstants.delivery_time);
                tvFoodTemperature.setText(LanguageConstants.food_temperature);
                tvFoodCustomerService.setText(LanguageConstants.customer_service);
                tvFoodComments.setText(LanguageConstants.comments);

                ivFoodRestaurants = dialog.findViewById(R.id.ivFoodRestaurants);
                tvFoodRestaurantNames.setText(orderlists.getVendor_name());
                tvFoodCuisine.setText(orderlists.getCuisine_name());
                String itemImages = Urls.BASE_URL_STAGING + orderlist.getVendor_image();
                CommonFunctions.getInstance().loadImageByFresco(ivFoodRestaurants, itemImages);

                rtbFoodDeliveryTimeRating = dialog.findViewById(R.id.rtbFoodDeliveryTimeRating);
                rtbFoodTemperatureRating = dialog.findViewById(R.id.rtbFoodTemperatureRating);
                rtbFoodCustomerServiceRating = dialog.findViewById(R.id.rtbFoodCustomerServiceRating);

                ivRestaurantRatingViewclose = dialog.findViewById(R.id.ivRestaurantRatingViewclose);

                ivRestaurantRatingViewclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });





                final String vendor_id = orderlists.getVendor_id();
                final String user_id = orderlists.getUser_id();
                final String orderid = orderlists.getOrder_id();

                rtbFoodDeliveryTimeRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        tvFoodDeliveryTimeRatingValue.setText("(" + String.valueOf(rating) + ")");
                    }
                });
                rtbFoodCustomerServiceRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        tvFoodCustomerServiceRatingValue.setText("(" + String.valueOf(rating) + ")");
                    }
                });
                rtbFoodTemperatureRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        tvFoodTemperatureRatingValue.setText("(" + String.valueOf(rating) + ")");
                    }
                });
                Button btNext = dialog.findViewById(R.id.btNext);
                btNext.setText(LanguageConstants.next);
                btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vendorComments = etFoodComment.getText().toString();


                        if (rtbFoodDeliveryTimeRating.getRating() == 0) {
                            Toast.makeText(mcontext, LanguageConstants.deliveryTimeRating, Toast.LENGTH_SHORT).show();
//                            CommonFunctions.getInstance().ShowSnackBar(mcontext, LanguageConstants.deliveryTimeRating);
                        } else if (rtbFoodTemperatureRating.getRating() == 0) {
                            Toast.makeText(mcontext, LanguageConstants.foodTempreatureRating, Toast.LENGTH_SHORT).show();
//                            CommonFunctions.getInstance().ShowSnackBar(mcontext, LanguageConstants.customerServiceRating);
                        } else if (rtbFoodCustomerServiceRating.getRating() == 0) {
                            Toast.makeText(mcontext, LanguageConstants.customerServiceRating, Toast.LENGTH_SHORT).show();
//                            CommonFunctions.getInstance().ShowSnackBar(mcontext, LanguageConstants.foodTempreatureRating);
                        } else if (etFoodComment.getText().toString().isEmpty()) {
                            Toast.makeText(mcontext, LanguageConstants.commentsEmpty, Toast.LENGTH_SHORT).show();
//                            CommonFunctions.getInstance().ShowSnackBar(mcontext, LanguageConstants.commentsEmpty);
                        } else {
                            deliveryTimeRating = String.valueOf(Math.round(rtbFoodDeliveryTimeRating.getRating()));
                            foodTemperatureRating = String.valueOf(Math.round(rtbFoodTemperatureRating.getRating()));
                            customerServiceRating = String.valueOf(Math.round(rtbFoodCustomerServiceRating.getRating()));
                            dialog.dismiss();
                            final Dialog dialog1 = new Dialog(mcontext);
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog1.setContentView(R.layout.dialog_addrestaurantrating);
                            if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    dialog1.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    dialog1.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                                }
                            }
                            ImageView ivclose, ivAddImage;
                            SimpleDraweeView ivRestaurant;
                            final EditText etcomments;
                            final TextView tvRestaurantName, tvCuisines, tvrateFoodservice,
                                    tvPhotos, tvComments;
                            Button btRate;
                            RecyclerView rvAddImage;
                            final RatingBar rtbTasteRating, rtbTimingRating, rtbPriceRating;
                            final RecyclerView rvAddRating;
                            tvRestaurantName = dialog1.findViewById(R.id.tvRestaurantName);
                            rvAddRating = dialog1.findViewById(R.id.rvAddRating);
                            tvrateFoodservice = dialog1.findViewById(R.id.tvrateFoodservice);

                            tvPhotos = dialog1.findViewById(R.id.tvPhotos);
                            tvComments = dialog1.findViewById(R.id.tvcomments);
                            ivAddImage = dialog1.findViewById(R.id.ivAddImage);
                            rvAddImage = dialog1.findViewById(R.id.rvAddImage);

                            tvrateFoodservice.setText(LanguageConstants.rateFoodService);
                            tvPhotos.setText(LanguageConstants.photos);
                            tvComments.setText(LanguageConstants.comments);

                            tvCuisines = dialog1.findViewById(R.id.tvCuisines);
                            ivRestaurant = dialog1.findViewById(R.id.ivRestaurants);
                            tvRestaurantName.setText(orderlists.getVendor_name());
                            tvCuisines.setText(orderlists.getCuisine_name());
                            String itemImages = Urls.BASE_URL_STAGING + orderlist.getVendor_image();
                            CommonFunctions.getInstance().loadImageByFresco(ivRestaurant, itemImages);

                            etcomments = dialog1.findViewById(R.id.etcomment);

                            btRate = dialog1.findViewById(R.id.btRate);
                            btRate.setText(LanguageConstants.rate);

                            btRate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    /*if (rtbTasteRating.getRating() == 0) {
                                        Toast.makeText(mcontext, LanguageConstants.tasteRating + LanguageConstants.isEmpty, Toast.LENGTH_SHORT).show();
//
                                    } else if (rtbTimingRating.getRating() == 0) {
                                        Toast.makeText(mcontext, LanguageConstants.timing + " " + LanguageConstants.rating + LanguageConstants.isEmpty, Toast.LENGTH_SHORT).show();
//
                                    } else if (rtbPriceRating.getRating() == 0) {
                                        Toast.makeText(mcontext, LanguageConstants.price + " " + LanguageConstants.rating + LanguageConstants.isEmpty, Toast.LENGTH_SHORT).show();
//
                                    }*/ /*if (etcomments.getText().toString().isEmpty()) {
                                        Toast.makeText(mcontext, LanguageConstants.commentsEmpty, Toast.LENGTH_SHORT).show();
////
                                    } else {*/

                                    RatingInputModel ratingModel = new RatingInputModel();
                                    ratingModel.setUserId(user_id);
                                    ratingModel.setVendorId(vendor_id);
                                    ratingModel.setVendorType("1");
                                    ratingModel.setOrderId(orderid);
                                    ratingModel.setItem(model);
                                    ratingModel.setFoodTemperature(foodTemperatureRating);
                                    ratingModel.setCustomerService(customerServiceRating);
                                    ratingModel.setDeliveryTime(deliveryTimeRating);
                                    ratingModel.setVendorRatingReviewText(vendorComments);
                                    Gson gson = new Gson();
                                    String RatingInput = gson.toJson(ratingModel);
                                    System.out.println("Input ==> " + RatingInput);
                                    dialog1.dismiss();
                                    CommonApiCalls.getInstance().addRestaurantRating(mcontext, RatingInput, new CommonCallback.Listener() {
                                        @Override
                                        public void onSuccess(Object body) {
                                            AddRestaurantRatingApiResponse addRestaurantRatingApiResponse = (AddRestaurantRatingApiResponse) body;
                                            Toast.makeText(mcontext,LanguageConstants.RatedSucessfully,Toast.LENGTH_LONG).show();

                                        }

                                        @Override
                                        public void onFailure(String reason) {

                                        }
                                    });


//                                        final String etComments = etcomments.getText().toString();
                                        /*RatingModel ratingModel = ratingItems;
                                        ratingModel.setFood_temperature(foodTemperatureRating);
                                        ratingModel.setCustomer_service(customerServiceRating);
                                        ratingModel.setDelivery_time(deliveryTimeRating);
                                        ratingModel.setVendor_rating_review_text(vendorComments);
                                        ratingModel.setVendor_id(vendor_id);
                                        ratingModel.setUser_id(user_id);
                                        ratingModel.setOrder_id(orderid);
                                        ratingModel.getItem().get(0).setItem_rating_review_text(etComments);*/


                                }
//                                }
                            });

                            ivclose = dialog1.findViewById(R.id.ivRatingViewclose);
                            //ItemRating.
                            String orderKey = orderlist.getOrder_key();
                            CommonApiCalls.getInstance().orderDetails(mcontext, orderKey, new CommonCallback.Listener() {
                                @Override
                                public void onSuccess(Object body) {
                                    OrderDetailsApiResponse orderDetailsApiResponse = (OrderDetailsApiResponse) body;
                                    orderDetailItems = orderDetailsApiResponse.getData().getOrder_details().getItems();
                                    RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(mcontext);
                                    rvAddRating.setLayoutManager(mlayoutManager);

                                    for (int count = 0; count < orderDetailItems.size(); count++) {
                                        orderDetailItems.get(count).setRandom_key(count);
                                    }

                                    ItemRatingAdapter itemRatingAdapter = new ItemRatingAdapter(mcontext, orderDetailItems);
                                    rvAddRating.setAdapter(itemRatingAdapter);

                                }

                                @Override
                                public void onFailure(String reason) {

                                }
                            });

                            final AddRatedFoodImageAdapter addRatedFoodImageAdapter;
                            addRatedFoodImageAdapter = new AddRatedFoodImageAdapter(mcontext);
                            rvAddImage.setAdapter(addRatedFoodImageAdapter);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            rvAddImage.setLayoutManager(horizontalLayoutManager);
                            ivAddImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                captureImage();
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    if (!Settings.System.canWrite(mcontext)) {
//                                        ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                                Manifest.permission.READ_EXTERNAL_STORAGE
//                                        }, 2910);
//                                        RatedItemImage ratedItemImage = new RatedItemImage();
//                                        imagePath1 = ratedItemImage.paymentcameraIntent((Activity) mcontext);
//                                    } else {
//                                        RatedItemImage ratedItemImage = new RatedItemImage();
//                                        imagePath1 = ratedItemImage.paymentcameraIntent((Activity) mcontext);
//                                    }
//                                }
                                }
                            });

                            ivclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });

                            Window window = dialog1.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.copyFrom(dialog1.getWindow().getAttributes());
                            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            wlp.gravity = Gravity.CENTER;
                            window.setAttributes(wlp);
                            dialog1.show();
                        }
                    }
                });

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.copyFrom(dialog.getWindow().getAttributes());
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);
                dialog.show();
            }
        });

        holder.im_vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderKey", orderlist.getOrder_key());
                CommonFunctions.getInstance().newIntent(mcontext, OrderDetailsActivity.class, bundle, false);
            }
        });
        holder.im_trackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                CommonFunctions.getInstance().newIntent(mcontext, TrackOrderActivity.class, bundle, false);
            }
        });
        holder.im_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                CommonFunctions.getInstance().newIntent(mcontext, RestaurantDetailsActivity.class, bundle, false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    /*
 * Capturing Camera Image will lauch camera app requrest image capture
 */

}


