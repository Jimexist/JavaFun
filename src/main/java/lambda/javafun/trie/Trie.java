package lambda.javafun.trie;

import com.google.common.base.Optional;

public interface Trie<T> {

    Optional<T> find(String key);

    Trie<T> insert(String key, T val);

}
