package com.zoka.sunshineapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zoka.sunshineapp.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import static com.zoka.sunshineapp.utils.NetworkUtils.JsonResponse;
import static com.zoka.sunshineapp.utils.NetworkUtils.buildQueryParam;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<String[]> {
    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecyclerView;
    private static final int LOADER_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mForecastAdapter = new ForecastAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mForecastAdapter);

        return rootView;
    }

    private String getLocationString() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.location_key),
                getString(R.string.location_default_value));
        return location;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
          startActivity(new Intent(getContext(),SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<String[]> onCreateLoader(int id, final Bundle args) {


        return new AsyncTaskLoader<String[]>(getActivity()) {
            String[] forecastData;

            @Override
            protected void onStartLoading() {

                Log.i("ZOKA", "start Loading !");
                if (forecastData != null) {
                    deliverResult(forecastData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(String[] data) {
                forecastData = data;
                super.deliverResult(data);
            }

            @Override
            public String[] loadInBackground() {
                try {
                    String location = getLocationString();
                    URL url = buildQueryParam(location);
                    String jsonResponse = JsonResponse(url);
                    String[] data = JsonUtils.getWeatherDataFromJson(jsonResponse);
                    return data;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new String[0];
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {

        mForecastAdapter.setWeatherData(data);
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }
}