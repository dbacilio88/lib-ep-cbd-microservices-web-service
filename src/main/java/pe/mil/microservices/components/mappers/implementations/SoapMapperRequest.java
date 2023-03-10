package pe.mil.microservices.components.mappers.implementations;

import pe.mil.microservices.components.mappers.contracts.ISoapMapperRequest;

import java.util.ArrayList;
import java.util.List;

public abstract class SoapMapperRequest<S, R> implements ISoapMapperRequest<S, R> {


    @Override
    public List<R> mapRequestByList(List<S> source) {

        if (source == null) {
            return null;
        }

        final List<R> m = new ArrayList<>(source.size());

        for (S s : source) {
            m.add(mapRequestBySource(s));
        }

        return m;
    }

    @Override
    public Iterable<R> mapRequestByIterable(Iterable<S> source) {

        if (source == null) {
            return null;
        }

        final ArrayList<R> m = new ArrayList<>();

        for (S s : source) {
            m.add(mapRequestBySource(s));
        }

        return m;
    }

    @Override
    public ArrayList<R> mapRequestByArrayList(ArrayList<S> source) {

        if (source == null) {
            return null;
        }

        final ArrayList<R> m = new ArrayList<>();

        for (S s : source) {
            m.add(mapRequestBySource(s));
        }

        return m;
    }
}
