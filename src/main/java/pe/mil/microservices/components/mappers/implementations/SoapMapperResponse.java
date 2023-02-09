package pe.mil.microservices.components.mappers.implementations;

import pe.mil.microservices.components.mappers.contracts.ISoapMapperResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class SoapMapperResponse<S, R> implements ISoapMapperResponse<S, R> {

    @Override
    public List<R> mapResponseByList(List<S> source) {
        if (source == null) {
            return null;
        }
        final List<R> r = new ArrayList<>(source.size());

        for (S s : source) {
            r.add(mapResponseBySource(s));
        }
        return r;
    }

    @Override
    public Iterable<R> mapResponseByIterable(Iterable<S> source) {
        if (source == null) {
            return null;
        }
        final ArrayList<R> r = new ArrayList<>();

        for (S s : source) {
            r.add(mapResponseBySource(s));
        }
        return r;
    }

    @Override
    public ArrayList<R> mapResponseByArrayList(ArrayList<S> source) {
        if (source == null) {
            return null;
        }
        final ArrayList<R> r = new ArrayList<>(source.size());

        for (S s : source) {
            r.add(mapResponseBySource(s));
        }
        return r;
    }
}
