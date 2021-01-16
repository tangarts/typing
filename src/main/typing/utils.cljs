(ns typing.utils
  (:require
    [reagent.core :as r]
    [clojure.string :as str]))

(comment 
  ; code for random stuff
  ; and utils like timer
)

(defn timer-component []
  (let [seconds-elapsed (r/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 1000)
      [:div
       "Seconds Elapsed: " @seconds-elapsed])))

(defn randint
  [start end]
  (+ start (int (* end (rand)))))

(def vocab (str/split "about all also and because but by can come could) day even find first for from get give go have he her here him his how if in into it its just know like look make man many me more my new no not now of on one only or other our out people say see she so some take tell than that the their them then there these they thing think this those time to two up use very want way we well what when which who will with would year you your" #" "))

(defn generate-words
  [n]
  (into []
        (for [x (range n)]
          (nth vocab (randint 0 (- (count vocab) 1))))))


(def typing-state
  #{"not-started"
    "in-prog"
    "awaiting"
    "finished"})


(def cpm (r/atom 0))

(defonce app-state (r/atom
             {:i 0
              :typing-state
              (typing-state "not-started")}))


(defn get-cpm
  [input-words]
  (let [time-elapsed (@app-state :time)
        characters (reduce + (map count input-words))]
    (/ (Math/floor (* characters 60)) time-elapsed)))




