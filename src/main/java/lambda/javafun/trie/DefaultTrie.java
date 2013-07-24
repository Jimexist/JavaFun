package lambda.javafun.trie;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultTrie<T> implements Trie<T> {

    private final List<Edge<T>> edges = Lists.newArrayList();
    private T value;

    DefaultTrie(T value) {
        this.value = checkNotNull(value);
    }

    private Optional<? extends Edge<T>> getEdge(final char c) {
        return Iterables.tryFind(edges, new Predicate<Edge<T>>() {
            @Override
            public boolean apply(final Edge<T> o) {
                return o != null && o.c == c;
            }
        });
    }

    @Override
    public Optional<T> find(String key) {
        return find(key.toCharArray(), 0);
    }

    private Optional<T> find(final char[] chars, int offset) {
        DefaultTrie<T> trie = this;
        while (offset < chars.length) {
            final char c = chars[offset++];
            final Edge<T> edge = trie.getEdge(c).orNull();
            if (edge == null) {
                return Optional.absent();
            } else {
                trie = edge.trie;
            }
        }
        return Optional.of(trie.value);
    }

    @Override
    public Trie<T> insert(String key, T val) {
        return insert(key.toCharArray(), 0, val);
    }

    private Trie<T> insert(final char[] chars, int offset, T val) {
        DefaultTrie<T> trie = this;
        while (offset < chars.length) {
            final char c = chars[offset++];
            final Edge<T> edge = trie.getEdge(c).orNull();
            if (edge == null) {
                trie.edges.add(Edge.of(c, val));
            } else {
                trie.value = val;
            }
        }
        return null;
    }

    static class Edge<T> {
        final char c;
        final DefaultTrie<T> trie;

        Edge(char c, DefaultTrie<T> trie) {
            this.c = c;
            this.trie = trie;
        }

        static <T> Edge<T> of(final char c, final T value) {
            return new Edge<T>(c, new DefaultTrie<T>(value));
        }
    }
}
