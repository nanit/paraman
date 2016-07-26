(ns paraman.core
  (:require [clojure.string :refer [join]])
  (:import [java.net URLEncoder]))

(defn- encode [s] (if (nil? s) "" (URLEncoder/encode s)))

(declare to-kv)

(defn- keyword->str [k]
  (if (nil? k) "" (name k)))

(defn- bracketize [s inner-map?]
  (if (and inner-map? (not-empty s)) (str "[" s "]") s))

(defn- kv->str [obj]
  (str (encode (:prefix obj)) (encode (bracketize (keyword->str (:key obj)) (:inner-map obj))) "=" (encode (:value obj))))

(defn- convert-vector [acc v prefix]
  (reduce 
    (fn [acc item] 
      (to-kv acc nil item true prefix))
    acc 
    v))

(defn- convert-map [acc v prefix]
  (reduce-kv
    (fn [acc k item] (to-kv acc k item true prefix))
    acc 
    v))

(defn- to-kv 
  ([acc k v] (to-kv acc k v false nil))
  ([acc k v inner-map? current-prefix]
   (cond 
     (string? v) (conj acc {:key k :value v :inner-map inner-map? :prefix current-prefix})
     (vector? v) (convert-vector acc v (str current-prefix (keyword->str k) "[]"))
     (map?    v) (convert-map acc v (str current-prefix (bracketize (keyword->str k) inner-map?))))))

(defn convert [m]
  (join "&" 
        (map kv->str
             (reduce-kv to-kv [] m))))
