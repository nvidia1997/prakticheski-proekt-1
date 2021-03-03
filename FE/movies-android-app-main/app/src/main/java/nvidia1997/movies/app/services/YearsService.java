package nvidia1997.movies.app.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Consumer;

import nvidia1997.movies.app.core.year.YearDomain;

public class YearsService {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchYears(Consumer<YearDomain[]> onSuccess) {
        YearDomain[] list = new YearDomain[40];

        int _year = 2021;
        for (int i = 0; i < list.length; i++) {
            list[i] = new YearDomain(_year);
            _year--;
        }

        onSuccess.accept(list);
    }
}
