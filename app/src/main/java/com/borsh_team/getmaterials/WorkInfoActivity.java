package com.borsh_team.getmaterials;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borsh_team.getmaterials.Adapter.ResourcesAdapter;
import com.borsh_team.getmaterials.Data.Resource;
import com.borsh_team.getmaterials.Data.WorkType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkInfoActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private String buildingType, capacity, capacityMark;
    private String[] worksID;
    private HashMap<String, Boolean> workLoadStatus;
    private ArrayList<WorkType> works;
    private ScrollView root;
    private TextView buildingTypeText, capacityText, worksText, fireSafetyText;
    private Button callSellerButton, goToWebsite, findAnotherButton;
    private RecyclerView resourceList;
    private ResourcesAdapter resourcesAdapter;
    private ImageView interpanLogo;
    private TextView allMaterialsAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        database = FirebaseDatabase.getInstance();
        works = new ArrayList<>();
        initializeIntent(getIntent());
        initializeViews();
        initializeLoadStatus();
        loadWorksInfo();
    }

    private void initializeIntent(Intent intent) {
        buildingType = intent.getExtras().getString("buildingType");
        capacity = intent.getExtras().getString("capacity");
        capacityMark = intent.getExtras().getString("capacityMark");
        worksID = intent.getExtras().getStringArray("worksID");
    }

    private void initializeViews() {
        interpanLogo = findViewById(R.id.interpan_logo);
        allMaterialsAllowed = findViewById(R.id.all_materials_allowed);
        root = findViewById(R.id.root);
        resourceList = findViewById(R.id.resourceList);
        buildingTypeText = findViewById(R.id.building_type_text);
        capacityText = findViewById(R.id.capacity_text);
        worksText = findViewById(R.id.works_text);
        fireSafetyText = findViewById(R.id.fire_safety_text);
        callSellerButton = findViewById(R.id.call_seller_button);
        callSellerButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:84991106630"));
            startActivity(intent);
        });
        goToWebsite = findViewById(R.id.go_to_website_button);
        goToWebsite.setOnClickListener(view -> goToSite());
        findAnotherButton = findViewById(R.id.find_another_button);
        findAnotherButton.setOnClickListener(view -> onBackPressed());
        initializeRecyclers();
    }

    private void initializeRecyclers() {
        resourcesAdapter = new ResourcesAdapter();
        resourceList.setLayoutManager(new LinearLayoutManager(this));
        resourceList.setAdapter(resourcesAdapter);
    }

    private void initializeLoadStatus() {
        // Нам нужно сохранять статус загрузки каждой работы, чтобы знать, когда экран
        // будет полностью загружен
        workLoadStatus = new HashMap<>();
        for (String workID : worksID) {
            workLoadStatus.put(workID, false);
        }
    }

    // String generators
    private String generateWorksString() {
        StringBuilder result = new StringBuilder();
        for (WorkType work: works) {
            result.append(work.getWorkTypeString());
        }
        return result.toString();
    }

    // Loaders
    private void loadWorksInfo() {
        for (String workID : worksID) {
            loadWorkInfo(workID);
        }
    }

    private void loadWorkInfo(String workID) {
        database.getReference("workTypes/" + workID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            WorkType workType = snapshot.getValue(WorkType.class);
                            works.add(workType);
                            addResources(workType.getResources());
                            onWorkLoaded(workID);
                            if (workType.getResources().get(0).getAllMaterialAllowed()) {
                                allMaterialsAllowed();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                }
        );
    }

    private void addResources(ArrayList<Resource> resources) {
        for (Resource resourceItem: resources) {
            resourcesAdapter.addResource(resourceItem);
        }
    }

    // Events
    private void onWorkLoaded(String workID) {
        workLoadStatus.put(workID, true);
        if (checkIfScreenLoaded()) {
            displayWorksInfo();
            showScreen();
        }
    }

    private void displayWorksInfo() {
        buildingTypeText.setText(buildingType);
        capacityText.setText(capacity);
        worksText.setText(generateWorksString());
        fireSafetyText.setText("Уровень пожарной безопасности: КМ" + capacityMark);
    }

    private boolean checkIfScreenLoaded() {
        for (Boolean workLoaded: workLoadStatus.values()) {
            if (!workLoaded) {
                return false;
            }
        }
        return true;
    }

    private void allMaterialsAllowed() {
        View.OnClickListener siteListener = view -> goToSite();
        resourceList.setVisibility(View.GONE);
        interpanLogo.setVisibility(View.VISIBLE);
        allMaterialsAllowed.setVisibility(View.VISIBLE);
        interpanLogo.setOnClickListener(siteListener);
        allMaterialsAllowed.setOnClickListener(siteListener);
    }

    // Animations
    private void showScreen() {
        root.setVisibility(View.VISIBLE);
        root.animate().alpha(1f).translationY(0f).setDuration(500L);
    }

    // Redirects
    private void goToSite() {
        String websiteUrl = "https://interpan.ru/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WorkSelectActivity.class);
        root.animate().alpha(0f).translationY(28f).setDuration(500L).setListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startActivity(intent);
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {}
                    @Override
                    public void onAnimationRepeat(Animator animator) {}
                }
        );
    }
}