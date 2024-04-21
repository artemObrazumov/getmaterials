package com.borsh_team.getmaterials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.borsh_team.getmaterials.Adapter.BuildingsListAdapter;
import com.borsh_team.getmaterials.Adapter.CapacityAdapter;
import com.borsh_team.getmaterials.Adapter.WorksListAdapter;
import com.borsh_team.getmaterials.Data.BuildingType;
import com.borsh_team.getmaterials.Data.BuildingTypeMenu;
import com.borsh_team.getmaterials.Data.CapacityType;
import com.borsh_team.getmaterials.Data.WorkTypeMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkSelectActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private BuildingTypeMenu building;
    private ConstraintLayout screen1, screen2, screen3;
    private RecyclerView buildingsList, capacityList, worksList;
    private BuildingsListAdapter buildingsAdapter;
    private CapacityAdapter capacityAdapter;
    private WorksListAdapter worksAdapter;
    private Button back2, back3, finishButton;
    private String buildingTypeID;
    private String capacity, capacityMark;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        intent = new Intent(this, WorkInfoActivity.class);
        initializeViews();
        initializeRecyclers();
        start();
    }

    private void start() {
        getBuildingsList();
    }

    private void initializeViews() {
        screen1 = findViewById(R.id.screen1);
        screen2 = findViewById(R.id.screen2);
        screen3 = findViewById(R.id.screen3);
        buildingsList = findViewById(R.id.buildingsList);
        capacityList = findViewById(R.id.capacityList);
        worksList = findViewById(R.id.worksList);
        back2 = findViewById(R.id.back2);
        back2.setOnClickListener(view -> {
            hideScreen2();
            showScreen1();
        });
        back3 = findViewById(R.id.back3);
        back3.setOnClickListener(view -> {
            hideScreen3();
            showScreen2();
        });
        finishButton = findViewById(R.id.finish_button);
        finishButton.setOnClickListener(view -> {
            finishSelecting();
        });
    }

    private void initializeRecyclers() {
        buildingsAdapter = new BuildingsListAdapter();
        buildingsList.setAdapter(buildingsAdapter);
        buildingsList.setLayoutManager(new LinearLayoutManager(this));
        buildingsAdapter.setListener(this::onBuildingTypeSelected);

        capacityAdapter = new CapacityAdapter();
        capacityList.setAdapter(capacityAdapter);
        capacityList.setLayoutManager(new LinearLayoutManager(this));
        capacityAdapter.setListener(this::onCapacityMarkSelected);

        worksAdapter = new WorksListAdapter();
        worksList.setAdapter(worksAdapter);
        worksList.setLayoutManager(new LinearLayoutManager(this));
    }

    // Data Searcher
    private void getBuildingsList() {
        ArrayList<BuildingTypeMenu> buildingsList = new ArrayList<>();
        database.getReference("buildingTypesMenu")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot buildingItem : snapshot.getChildren()) {
                            BuildingTypeMenu building = buildingItem.getValue(BuildingTypeMenu.class);
                            buildingsList.add(building);
                        }
                        buildingsAdapter.setDataSet(buildingsList);
                        onBuildingsListLoaded();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void getCapacityList() {
        ArrayList<CapacityType> capacityTypesList = new ArrayList<>();
        database.getReference("buildingTypes/" + buildingTypeID + "/capacity")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot capacityItem : snapshot.getChildren()) {
                            CapacityType capacity = capacityItem.getValue(CapacityType.class);
                            capacityTypesList.add(capacity);
                        }
                        capacityAdapter.setDataSet(capacityTypesList);
                        onCapacityListLoaded();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void getWorksList() {
        ArrayList<WorkTypeMenu> worksList = new ArrayList<>();
        database.getReference("buildingTypes/" + buildingTypeID + "/workTypes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot workItemSnapshot : snapshot.getChildren()) {
                            WorkTypeMenu workItem = workItemSnapshot.getValue(WorkTypeMenu.class);
                            worksList.add(workItem);
                        }
                        worksAdapter.setDataSet(worksList);
                        onWorksListLoaded();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    // Events
    private void onBuildingTypeSelected(BuildingTypeMenu building) {
        this.building = building;
        this.buildingTypeID = building.getBuildingTypeID();
        getCapacityList();
        hideScreen1();
    }

    private void onCapacityMarkSelected(CapacityType capacity) {
        this.capacityMark = capacity.getCapacityMark();
        this.capacity = capacity.getCapacityString();
        getWorksList();
        hideScreen2();
    }

    private void onBuildingsListLoaded() {
        showScreen1();
    }

    private void onCapacityListLoaded() {
        showScreen2();
    }

    private void onWorksListLoaded() {
        showScreen3();
    }

    // Animations
    private void showScreen1() {
        screen1.setVisibility(View.VISIBLE);
        screen1.animate().alpha(1f).translationY(0f).setDuration(500L).setListener(null);
    }

    private void hideScreen1() {
        screen1.animate().alpha(0f).translationY(28f).setDuration(500L).setStartDelay(0L)
                .setListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                screen1.setVisibility(View.GONE);
                                animator.removeAllListeners();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        }
                );
    }

    private void showScreen2() {
        screen2.setVisibility(View.VISIBLE);
        screen2.animate().alpha(1f).translationY(0f).setDuration(500L).setStartDelay(400L)
                .setListener(null);
    }

    private void hideScreen2() {
        screen2.animate().alpha(0f).translationY(16f).setDuration(500L).setStartDelay(0L)
            .setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            screen2.setVisibility(View.GONE);
                            animator.removeAllListeners();
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    }
            );
    }

    private void showScreen3() {
        screen3.setVisibility(View.VISIBLE);
        screen3.animate().alpha(1f).translationY(0f).setDuration(500L).setStartDelay(400L)
                .setListener(null);
    }

    private void hideScreen3() {
        screen3.animate().alpha(0f).translationY(16f).setDuration(500L).setStartDelay(0L)
                .setListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                screen3.setVisibility(View.GONE);
                                animator.removeAllListeners();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        }
                );
    }

    // Finishing
    private void finishSelecting() {
        screen3.animate().alpha(0f).translationY(16f).setDuration(500L).setStartDelay(0L)
                .setListener(
                        new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                if (getWorksIdOrNull() == null) {
                                    Toast.makeText(getBaseContext(), "Выберите хотя бы один тип работ!", Toast.LENGTH_SHORT)
                                            .show();
                                    showScreen3();
                                } else {
                                    intent.putExtra("buildingType", building.getBuildingType());
                                    intent.putExtra("capacity", capacity);
                                    intent.putExtra("capacityMark", capacityMark);
                                    ArrayList<String> worksID = getWorksIdOrNull();
                                    String[] worksIDArray = new String[worksID.size()];
                                    worksIDArray = worksID.toArray(worksIDArray);
                                    intent.putExtra("worksID", worksIDArray);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        }
                );

    }

    private ArrayList<String> getWorksIdOrNull() {
        ArrayList<String> worksID = new ArrayList<>();
        for (int i = 0;i < worksAdapter.getItemCount(); i++) {
            WorksListAdapter.ViewHolder holder =
                    (WorksListAdapter.ViewHolder) worksList.findViewHolderForAdapterPosition(i);
            if (holder.isChecked()) {
                worksID.add(holder.getWorkID() + "_" + capacityMark);
            }
        }
        if (worksID.size() == 0) {
            return null;
        } else {
            return worksID;
        }
    }

    @Override
    public void onBackPressed() {
        if (screen1.getVisibility() == View.VISIBLE) {
            // pass
        } else if (screen2.getVisibility() == View.VISIBLE) {
            back2.callOnClick();
        } else {
            back3.callOnClick();
        }
    }
}