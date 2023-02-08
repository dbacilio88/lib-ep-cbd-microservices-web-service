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
        final List<R> result = new ArrayList<>(source.size());

        for (S sourceInstance : source) {
            result.add(mapRequestBySource(sourceInstance));
        }

        return result;
    }

    @Override
    public Iterable<R> mapRequestByIterable(Iterable<S> source) {

        if (source == null) {
            return null;
        }

        final ArrayList<R> result = new ArrayList<>();

        for (S sourceInstance : source) {
            result.add(mapRequestBySource(sourceInstance));
        }

        return result;
    }

    @Override
    public ArrayList<R> mapRequestByArrayList(ArrayList<S> source) {

        if (source == null) {
            return null;
        }

        final ArrayList<R> result = new ArrayList<>();

        for (S sourceInstance : source) {
            result.add(mapRequestBySource(sourceInstance));
        }
        return result;
    }
}
