package com.example.proiectdam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.proiectdam.R;

public class GuestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);

        // Get the WebView from the layout
        WebView spotifyWebView = view.findViewById(R.id.spotifyWebView);

        // Enable JavaScript for the WebView
        WebSettings webSettings = spotifyWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        // Make sure that links open inside the WebView (not in a browser)
        spotifyWebView.setWebViewClient(new WebViewClient());

        // Load the Spotify playlist URL
        spotifyWebView.loadUrl("https://open.spotify.com/embed/playlist/0FagkDQFBb3d63Oxf3Xs3t?utm_source=generator");

        return view;
    }
}
