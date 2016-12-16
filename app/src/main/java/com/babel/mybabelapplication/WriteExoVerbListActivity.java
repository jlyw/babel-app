package com.babel.mybabelapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.UserDAO;
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class WriteExoVerbListActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private boolean isFrench;
    private boolean isInfinitive;
    private boolean isSimplePast;
    private VerbListDAO verbListDAO;
    private VerbDAO verbDAO;
    private UserDAO userDAO;
    private String listVerbId;
    private int index;
    private int[] listOfIndex;
    private int[] listOfSuccess;
    private VerbList verbList;
    private List<Verb> verbs;
    private Intent intent;

    @BindView(R.id.text_view_french)
    protected TextView text_view_french;

    @BindView(R.id.edit_view_french)
    protected EditText edit_view_french;

    @BindView(R.id.text_view_infinitive)
    protected TextView text_view_infinitive;

    @BindView(R.id.edit_view_infinitive)
    protected EditText edit_view_infinitive;

    @BindView(R.id.text_view_simple_past)
    protected TextView text_view_simple_past;

    @BindView(R.id.edit_view_simple_past)
    protected EditText edit_view_simple_past;

    @BindView(R.id.text_view_past_participle)
    protected TextView text_view_past_participle;

    @BindView(R.id.edit_view_past_participle)
    protected EditText edit_view_past_participle;



    @BindView(R.id.text_view_indexing)
    protected TextView textViewIndexing;

    @BindView(R.id.valid_voc_exo_button)
    protected Button buttonValidAnswer;

    @BindView(R.id.next_voc_exo_button)
    protected Button buttonNextExo;
    private Verb verb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_exo_verb_list);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        isFrench = false;
        isInfinitive = false;
        isSimplePast = false;
        double random = Math.random();
        if (random < 0.25) {
            isFrench = true;
        } else if (random < 0.5) {
            isInfinitive = true;
        } else if (random < 0.75) {
            isSimplePast = true;
        }

        verbListDAO = new VerbListDAO();
        verbDAO = new VerbDAO();
        userDAO = new UserDAO();

        listVerbId = getIntent().getStringExtra("LIST_VOC_ID");
        index = getIntent().getIntExtra("INDEX", 0);
        listOfIndex = getIntent().getIntArrayExtra("RANDOM_INDEXING");
        listOfSuccess = getIntent().getIntArrayExtra("SUCCESS_LIST");

        verbList = verbListDAO.getVerbList(listVerbId);
        verbs = verbDAO.getAllVerbsOffOneList(listVerbId);

        toolbar.setTitle(verbList.getName());
        if (isFrench) {
            text_view_french.setText(verbs.get(listOfIndex[index]).getFrench());
            text_view_french.setVisibility(View.VISIBLE);
            edit_view_french.setVisibility(View.GONE);
        } else if (isInfinitive) {
            text_view_infinitive.setText(verbs.get(listOfIndex[index]).getInfinitive());
            text_view_infinitive.setVisibility(View.VISIBLE);
            edit_view_infinitive.setVisibility(View.GONE);
        } else if (isSimplePast) {
            text_view_simple_past.setText(verbs.get(listOfIndex[index]).getSimplePast());
            text_view_simple_past.setVisibility(View.VISIBLE);
            edit_view_simple_past.setVisibility(View.GONE);
        } else {
            text_view_past_participle.setText(verbs.get(listOfIndex[index]).getPastParticiple());
            text_view_past_participle.setVisibility(View.VISIBLE);
            edit_view_past_participle.setVisibility(View.GONE);
        }
        /*textViewIndexing.setText(String.valueOf(index + 1) + " sur " + verbs.size() + "       " +
                String.valueOf(listOfSuccess[0]) + " " + String.valueOf(listOfSuccess[1]) + " " + String.valueOf(listOfSuccess[2]) +
                "       " + String.valueOf(verbs.get(listOfIndex[index]).getGrade())
        );*/
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(getApplicationContext(), ShowVerbListActivity.class);
        intent.putExtra("LIST_VOC_ID", listVerbId);
        startActivity(intent);
    }

    @OnClick(R.id.valid_voc_exo_button)
    public void validVerbWriteExo() {
        listOfSuccess[index] = 0;
        if (isFrench) {
            String answerInfinitive = edit_view_infinitive.getText().toString().toLowerCase();
            String answerSimplePast = edit_view_simple_past.getText().toString().toLowerCase();
            String answerFastPasticiple = edit_view_past_participle.getText().toString().toLowerCase();
            if (answerInfinitive.isEmpty() || answerSimplePast.isEmpty() || answerFastPasticiple.isEmpty()) {
                showAlert(R.string.title_warning_exo_voc, R.string.azertyuiop);
            } else {
                verb = verbDAO.getVerb(verbs.get(listOfIndex[index]).getId());
                buttonValidAnswer.setVisibility(View.GONE);
                buttonNextExo.setVisibility(View.VISIBLE);

                if(Objects.equals(verbs.get(listOfIndex[index]).getInfinitive().toLowerCase(), answerInfinitive)) {
                    edit_view_infinitive.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_infinitive.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_infinitive.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getInfinitive());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getSimplePast().toLowerCase(), answerSimplePast)) {
                    edit_view_simple_past.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_simple_past.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_simple_past.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getSimplePast());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getPastParticiple().toLowerCase(), answerFastPasticiple)) {
                    edit_view_past_participle.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_past_participle.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_past_participle.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getPastParticiple());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

            }
        } else if (isInfinitive) {
            String answerFrench = edit_view_french.getText().toString().toLowerCase();
            String answerSimplePast = edit_view_simple_past.getText().toString().toLowerCase();
            String answerFastPasticiple = edit_view_past_participle.getText().toString().toLowerCase();
            if (answerFrench.isEmpty() || answerSimplePast.isEmpty() || answerFastPasticiple.isEmpty()) {
                showAlert(R.string.title_warning_exo_voc, R.string.azertyuiop);
            } else {
                verb = verbDAO.getVerb(verbs.get(listOfIndex[index]).getId());
                buttonValidAnswer.setVisibility(View.GONE);
                buttonNextExo.setVisibility(View.VISIBLE);

                if(Objects.equals(verbs.get(listOfIndex[index]).getFrench().toLowerCase(), answerFrench)) {
                    edit_view_french.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_french.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_french.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getFrench());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getSimplePast().toLowerCase(), answerSimplePast)) {
                    edit_view_simple_past.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_simple_past.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_simple_past.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getSimplePast());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getPastParticiple().toLowerCase(), answerFastPasticiple)) {
                    edit_view_past_participle.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_past_participle.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_past_participle.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getPastParticiple());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }
            }
        } else if (isSimplePast) {
            String answerFrench = edit_view_french.getText().toString().toLowerCase();
            String answerInfinitive = edit_view_infinitive.getText().toString().toLowerCase();
            String answerFastPasticiple = edit_view_past_participle.getText().toString().toLowerCase();
            if (answerInfinitive.isEmpty() || answerFrench.isEmpty() || answerFastPasticiple.isEmpty()) {
                showAlert(R.string.title_warning_exo_voc, R.string.azertyuiop);
            } else {
                verb = verbDAO.getVerb(verbs.get(listOfIndex[index]).getId());
                buttonValidAnswer.setVisibility(View.GONE);
                buttonNextExo.setVisibility(View.VISIBLE);

                if(Objects.equals(verbs.get(listOfIndex[index]).getFrench().toLowerCase(), answerFrench)) {
                    edit_view_french.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_french.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_french.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getFrench());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getInfinitive().toLowerCase(), answerInfinitive)) {
                    edit_view_infinitive.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_infinitive.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_infinitive.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getInfinitive());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getPastParticiple().toLowerCase(), answerFastPasticiple)) {
                    edit_view_past_participle.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_past_participle.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_past_participle.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_past_participle.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getPastParticiple());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }
            }
        } else {
            String answerFrench = edit_view_french.getText().toString().toLowerCase();
            String answerInfinitive = edit_view_infinitive.getText().toString().toLowerCase();
            String answerSimplePast = edit_view_simple_past.getText().toString().toLowerCase();
            if (answerInfinitive.isEmpty() || answerSimplePast.isEmpty() || answerFrench.isEmpty()) {
                showAlert(R.string.title_warning_exo_voc, R.string.azertyuiop);
            } else {
                verb = verbDAO.getVerb(verbs.get(listOfIndex[index]).getId());
                buttonValidAnswer.setVisibility(View.GONE);
                buttonNextExo.setVisibility(View.VISIBLE);

                if(Objects.equals(verbs.get(listOfIndex[index]).getFrench().toLowerCase(), answerFrench)) {
                    edit_view_french.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_french.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_french.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_french.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getFrench());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getInfinitive().toLowerCase(), answerInfinitive)) {
                    edit_view_infinitive.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_infinitive.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_infinitive.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_infinitive.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getInfinitive());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }

                if(Objects.equals(verbs.get(listOfIndex[index]).getSimplePast().toLowerCase(), answerSimplePast)) {
                    edit_view_simple_past.setTextColor(Color.parseColor("#85c35d"));
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_success);
                    userDAO.userGoodAnswer();
                    edit_view_simple_past.setText("Bien joué !");

                    listOfSuccess[index]++;
                    verbDAO.upVerbGrade(verb);
                } else {
                    edit_view_simple_past.setBackgroundResource(R.drawable.border_bottom_error);
                    edit_view_simple_past.setText("Dommage !\nLa bonne réponse était " + verbs.get(listOfIndex[index]).getSimplePast());
                    userDAO.userBadAnswer();
                    verbDAO.downVerbGrade(verb);
                }
            }
        }
    }

    @OnClick(R.id.next_voc_exo_button)
    public void nextVerbExo() {
        if(index+1 == verbs.size()) {
            userDAO.upUserExerciceDone();
            intent = new Intent(getApplicationContext(), ResultExoVerbActivity.class);

            intent.putExtra("LIST_VOC_ID", listVerbId);
            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);
            intent.putExtra("EXO_TYPE", "VOC_WRITE");

            startActivity(intent);
        } else {
            intent = new Intent(getApplicationContext(), WriteExoVerbListActivity.class);

            intent.putExtra("LIST_VOC_ID", listVerbId);
            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);
            intent.putExtra("INDEX", index+1);

            startActivity(intent);
        }
    }

    private void showAlert(int title, int message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alertDialog.show();
    }
}
