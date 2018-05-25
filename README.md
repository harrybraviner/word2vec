word2vec
========

My library to produce word embeddings using the skip-gram model of Mikolov et al.

Currently just some code to read in a text corpus via `CorpusParser`.

# ToDo

* Write a Stream<String> provider for Reuters.
* Write a function to randomly sample words and contexts.
* ~~Write an MXNet network to do lookup of embeddings and train.~~
  Without a complicated archtecture to back-propogate through, is it simpler to just do this using pure java?
