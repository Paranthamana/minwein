package com.minwein.customer.activity;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.minwein.customer.R;
import com.minwein.customer.adapter.NavigationMenuAdapter;
import com.minwein.customer.app_interfaces.NavigationClick;
import com.minwein.customer.enums.NavigationMenuEnum;
import com.minwein.customer.fragment.AddressListingFragment;
import com.minwein.customer.fragment.ContactUsFragment;
import com.minwein.customer.fragment.FaqFragment;
import com.minwein.customer.fragment.FavouritesFragment;
import com.minwein.customer.fragment.HomeCityAreaFragment;
import com.minwein.customer.fragment.LoginFragment;
import com.minwein.customer.fragment.LoyalityPointsFragment;
import com.minwein.customer.fragment.OffersFragment;
import com.minwein.customer.fragment.OrdersFragment;
import com.minwein.customer.fragment.ProfileFragment;
import com.minwein.customer.fragment.RatingAndReviewsFragment;
import com.minwein.customer.fragment.RestaurantsListingFragment;
import com.minwein.customer.model.NavigationMenuModel;
import com.minwein.customer.realm_db.RealmLibrary;
import com.minwein.customer.utils.AppSharedValues;
import com.minwein.customer.utils.CommonFunctions;
import com.minwein.customer.utils.LanguageConstants;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
        HomeCityAreaFragment.OnFragmentInteractionListener, RestaurantsListingFragment.OnFragmentInteractionListener,
        OrdersFragment.OnFragmentInteractionListener, AddressListingFragment.OnFragmentInteractionListener,
        RatingAndReviewsFragment.OnFragmentInteractionListener, LoyalityPointsFragment.OnFragmentInteractionListener,
        OffersFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,
        ContactUsFragment.OnFragmentInteractionListener, View.OnClickListener, FaqFragment.OnFragmentInteractionListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.lvNavigation)
    ListView lvNavigation;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.sdvProfilePicture)
    ImageView sdvProfilePicture;
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.ivEditProfile)
    ImageView ivEditProfile;
    @BindView(R.id.tvProfilePhonenumber)
    TextView tvProfilePhonenumber;
    //    @BindView(R.id.swLanguage)
//    Switch swLanguage;
    @BindView(R.id.btnEnglish)
    Button btnEnglish;
    @BindView(R.id.btnArabic)
    Button btnArabic;
    private FragmentManager fragmentManager;
    private boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }
        ivEditProfile.setOnClickListener(this);

        if (SessionManager.getInstance().getUserKey().isEmpty()) {
            tvProfileName.setText(LanguageConstants.guest);
//            tvProfilePhonenumber.setText("12345678");
            sdvProfilePicture.setImageResource(R.drawable.ic_userprofile);
            ivEditProfile.setVisibility(View.GONE);
        } else {
            tvProfileName.setText(SessionManager.getInstance().getUserFirstname() + " " + SessionManager.getInstance().getUserLastname());
            tvProfilePhonenumber.setText(String.valueOf(SessionManager.getInstance().getUserMobilenumber()));
            sdvProfilePicture.setImageResource(R.drawable.ic_userprofile);


        }

        loadNavigationMenu();

        fragmentManager = getSupportFragmentManager();


//        if (SessionManager.getInstance().getUserKey().isEmpty() && RealmLibrary.getInstance().getCartSize() != 0){
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.flContainer, new LoginFragment(), "login")
//                    .commit();
//        }
//        else
        if (AppSharedValues.latitude != 0.0 && AppSharedValues.longitude != 0.0) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, new RestaurantsListingFragment(), "restaurantList")
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, new HomeCityAreaFragment(), "cityArea")
                    .commit();
        }


        if (SessionManager.getInstance().getAppLanguage().equals("ar")) {
            btnArabic.setBackgroundResource(R.drawable.xml_greenrounded);
            btnEnglish.setBackgroundResource(0);
        } else {
            btnEnglish.setBackgroundResource(R.drawable.xml_greenrounded);
            btnArabic.setBackgroundResource(0);
        }

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SessionManager.getInstance().getAppLanguage().equals("ar")) {

                    btnArabic.setBackgroundResource(R.drawable.xml_greenrounded);
                    btnEnglish.setBackgroundResource(0);

                    SessionManager.getInstance().setAppLanguage("en");
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    LanguageConstants.getInstance().LanguageConstants();
                    Bundle bundle = new Bundle();
                    CommonFunctions.getInstance().newIntent(HomeActivity.this, SplashScreenActivity.class, bundle, true);

                } else {
                }

            }
        });

        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.getInstance().getAppLanguage().equals("en")) {

                    btnEnglish.setBackgroundResource(R.drawable.xml_greenrounded);
                    btnArabic.setBackgroundResource(0);

                    SessionManager.getInstance().setAppLanguage("ar");
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    LanguageConstants.getInstance().LanguageConstants();
                    Bundle bundle = new Bundle();
                    CommonFunctions.getInstance().newIntent(HomeActivity.this, SplashScreenActivity.class, bundle, true);
                } else {

                }


            }
        });
    }

    private void loadNavigationMenu() {

        List<NavigationMenuModel> navigationMenuModels = new ArrayList<>();

        NavigationMenuModel navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.HOME);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_home);
        navigationMenuModel.setNavigationItemName(LanguageConstants.home);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.ORDERS);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_orders);
        navigationMenuModel.setNavigationItemName(LanguageConstants.orders);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.CART);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_cartgreen1);
        navigationMenuModel.setNavigationItemName(LanguageConstants.cart);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.ADDRESS);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_address_sidemenu);
        navigationMenuModel.setNavigationItemName(LanguageConstants.address);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.RATE_AND_REVIEWS);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_rate_and_reviewssidemenu);
        navigationMenuModel.setNavigationItemName(LanguageConstants.rate_and_reviews);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.LOYALTY_POINTS);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_loyaltypoints);
        navigationMenuModel.setNavigationItemName(LanguageConstants.loyalty_points);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.OFFERS);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_offers);
        navigationMenuModel.setNavigationItemName(LanguageConstants.offers);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.FAVORITES);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_favourites_sidemenu);
        navigationMenuModel.setNavigationItemName(LanguageConstants.favorites);
        navigationMenuModels.add(navigationMenuModel);


        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.CONTACT_US);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_contact);
        navigationMenuModel.setNavigationItemName(LanguageConstants.contact_us);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.FAQ);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_faq);
        navigationMenuModel.setNavigationItemName(LanguageConstants.faq);
        navigationMenuModels.add(navigationMenuModel);

        navigationMenuModel = new NavigationMenuModel();
        navigationMenuModel.setNavigationItemId(NavigationMenuEnum.LOGOUT);
        navigationMenuModel.setNavigationIcon(R.drawable.ic_logout);
        navigationMenuModel.setNavigationItemName(LanguageConstants.logout);
        navigationMenuModels.add(navigationMenuModel);


        NavigationMenuAdapter navigationMenuAdapter = new NavigationMenuAdapter(HomeActivity.this, navigationMenuModels, new NavigationClick() {
            @Override
            public void onClick(NavigationMenuModel navigationMenuModel) {
                navigationMenuClick(navigationMenuModel);
            }
        });
        lvNavigation.setAdapter(navigationMenuAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            this.doubleBackToExitPressedOnce = true;
//            CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.exit_press_back_twice_message);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void navigationMenuClick(NavigationMenuModel navigationMenuModel) {
        {
            if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.HOME) {
                fragmentManager.beginTransaction()
                        .replace(R.id.flContainer, new HomeCityAreaFragment(), "home")
                        .addToBackStack("home")
                        .commit();
                AppSharedValues.latitude = 0.0;
                AppSharedValues.longitude = 0.0;
                AppSharedValues.foodOrCuisine = "";
            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.ORDERS) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new OrdersFragment(), "order")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.CART) {
//                if (SessionManager.getInstance().getUserKey().isEmpty()) {
//                    fragmentManager
//                            .beginTransaction()
//                            .replace(R.id.flContainer, new LoginFragment(), "login")
//                            .addToBackStack("login")
//                            .commit();
//                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
//                } else {
                    if (RealmLibrary.getInstance().getCartSize() == 0){
                        CartSummaryActivity.deliveryOptions.clear();
                        CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this,LanguageConstants.cartEmpty);
                    }else {
                        CommonFunctions.getInstance().newIntent(HomeActivity.this, CartSummaryActivity.class, Bundle.EMPTY, false);
                    }
//                }

            }else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.ADDRESS) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new AddressListingFragment(), "address")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.RATE_AND_REVIEWS) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new RatingAndReviewsFragment(), "rateAndReview")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.OFFERS) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new OffersFragment(), "offers")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.LOYALTY_POINTS) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new LoyalityPointsFragment(), "loyaltyPoints")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.FAVORITES) {
                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment(), "login")
                            .addToBackStack("login")
                            .commit();
                    CommonFunctions.getInstance().ShowSnackBar(HomeActivity.this, LanguageConstants.pleaselogintocontinue);
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new FavouritesFragment(), "favorites")
                            .addToBackStack("home")
                            .commit();
                }

            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.CONTACT_US) {

                fragmentManager.beginTransaction()
                        .replace(R.id.flContainer, new ContactUsFragment(), "contactUs")
                        .addToBackStack("contactUs")
                        .commit();
            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.FAQ) {
                fragmentManager.beginTransaction()
                        .replace(R.id.flContainer, new FaqFragment(), "faq")
                        .addToBackStack("home")
                        .commit();


            } else if (navigationMenuModel.getNavigationItemId() == NavigationMenuEnum.LOGOUT) {


                if (SessionManager.getInstance().getUserKey().isEmpty()) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.flContainer, new LoginFragment())
                            .commit();
                } else {
                    final Dialog dialog = new Dialog(HomeActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_alert);
                    TextView tvQue = dialog.findViewById(R.id.tvQue);
                    TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);
                    Button btnYes = dialog.findViewById(R.id.btnYes);
                    btnCancel.setText(LanguageConstants.cancel);
                    btnYes.setText(LanguageConstants.yes);
                    tvTitle.setText(LanguageConstants.alert);
                    tvQue.setText(LanguageConstants.areyousurewanttologout);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String language = SessionManager.getInstance().getAppLanguage();
                            SessionManager.getInstance().Logout();
                            AppSharedValues.foodOrCuisine = "";
                            AppSharedValues.latitude = 0.0;
                            AppSharedValues.longitude = 0.0;

                            Bundle bundle = new Bundle();
                            SessionManager.getInstance().setAppLanguage(language);
                            CommonFunctions.getInstance().newIntent(HomeActivity.this, SplashScreenActivity.class, bundle, true);
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    window.setAttributes(wlp);
                    dialog.show();

                }

            }

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivEditProfile) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, new ProfileFragment(), "ProfileDetails")
                    .addToBackStack("ProfileDetails")
                    .commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
//        if (v == swLanguage) {
//            if (SessionManager.getInstance().getAppLanguage().equals("en")) {
//                swLanguage.setChecked(false);
//                    SessionManager.getInstance().setAppLanguage("ar");
//            } else {
//                swLanguage.setChecked(true);
//                    SessionManager.getInstance().setAppLanguage("en");
//            }
//            LanguageConstants.getInstance().LanguageConstants();
//            Bundle bundle = new Bundle();
//            CommonFunctions.getInstance().newIntent(HomeActivity.this, SplashScreenActivity.class, bundle, true);
//        }

    }
}
