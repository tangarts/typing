(ns typing.utils
  (:require
    [reagent.core :as r]
    [clojure.string :as string]
    [typing.components.text :refer [road-not-taken-1 ex-1 voltaire yeats meditations-2-1 e-to-a seneca]]
    ))

(comment 
  ; code for random stuff
  ; and utils like timer
)

;; Compatability
;; Must extend number for js/Number since time is represented as milliseconds


(def cpm (r/atom 0))

(defn get-cpm
  [characters time]
  (let [time-elapsed time]
    (/ (Math/floor (* characters 60)) time-elapsed)))


(defn random-text []
  (rand-nth [road-not-taken-1 ex-1 voltaire yeats e-to-a seneca]))

(defn strip-text [text] 
  (string/split text #""))
