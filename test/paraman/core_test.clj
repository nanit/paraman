(ns paraman.core-test
  (:require [clojure.test :refer :all]
            [paraman.core :refer :all])
  (:import [java.net URLDecoder]))

(defn decode [s] (URLDecoder/decode s))

(deftest empty-map-test
  (is (= "" (decode (convert {})))))

(deftest basic-key-value-test
  (is (= "key1=val1&key2=val2" (decode (convert {:key1 "val1" :key2 "val2"})))))

(deftest number-test
  (is (= "key1=100" (decode (convert {:key1 100})))))

(deftest nil-test
  (is (= "key1=" (decode (convert {:key1 nil})))))

(deftest boolean-test
  (is (= "key1=false" (decode (convert {:key1 false})))))

(deftest keyword-value-test
  (is (= "key1=val1" (decode (convert {:key1 :val1})))))

(deftest basic-array-test
  (is (= "key1[]=val1&key1[]=val2" (decode (convert {:key1 ["val1" "val2"]})))))

(deftest array-with-keywords-test
  (is (= "key1[]=val1&key1[]=val2" (decode (convert {:key1 [:val1 :val2]})))))

(deftest mixed-basic-array-test
  (is (= "key1=val1&key2[]=val21&key2[]=val22" (decode (convert {:key1 "val1" :key2 ["val21" "val22"]})))))

(deftest nested-one-level-objects-test
  (is (= "key1[nested1]=val11&key1[nested2]=val12" (decode (convert {:key1 {:nested1 "val11" :nested2 :val12}})))))

(deftest nested-multiple-level-objects-test
  (is (= "key1[nested1][nested11]=val11&key1[nested1][nested12]=val12" (decode (convert {:key1 {:nested1 {:nested11 "val11" :nested12 "val12"}}})))))

(deftest array-of-objects
  (is (= "arr[][a]=b&arr[][c]=d&arr[][a]=e&arr[][c]=f" (decode (convert {:arr [{:a "b" :c "d"} {:a "e" :c "f"}]})))))

(deftest array-of-nested-objects
  (is (= "arr[][a]=b&arr[][c][cd]=d&arr[][a]=e&arr[][c][cf]=f" (decode (convert {:arr [{:a "b" :c {:cd "d"}} {:a "e" :c {:cf"f"}}]})))))
