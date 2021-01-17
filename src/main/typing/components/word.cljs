(ns typing.components.word
  (:require 
    [reagent.core :as r]
    [clojure.string :as str]))


(def word-css
  {:font-size 18
   :font-family "Source Code Pro, monospace"
   :margin "0 auto"
   :overflow "hidden"
   })

(defn character
  "if variant red else white "
  [children variant]
  (let [color
        (cond (= variant "white") "#fff"
              (= variant "black") "ff0033"
              :else "#111")]
    [:span {:style {:color color}} children]))

(comment 
 ; A character can be:
 ; waiting "" (or (nil? actual) (<= (count actual) i))
 ;
 ; Done -> {correct,incorrect} 
 ;
 ; current (= i (count @input-ref!)) 
 ;
 ; update class with atoms
 ; ["" "done" "current"]
  
)

; (defn character
;   "if variant red else white "
;   [children variant]
;   (let [color
;         (cond (= variant "white") "#fff"
;               (= variant "black") "ff0033"
;               :else "#111")
;         character-state (r/atom "")]
;     (fn [children variant]
;       [:span {:style {:color color}
;             :class [""]}
;      children])))

(defn get-variant 
  [expected actual i]
  (cond 
    (or (nil? actual) (<= (count actual) i)) "black"
    (= (nth expected i) (nth actual i)) "white"
    :else "red"))


(defn word 
  "variant: current done awaiting"
  [expected actual variant]
  (let [is-match (= expected actual)
        index (range (count expected))
        style-obj 
        (cond (= variant "done") 
              {:color (if is-match "#1c54ff" "#f20434")}
              (= variant "current")
              {:background "#3bd376"}
              :else {:color "#111"})]
    (into [:span {:style (merge word-css style-obj) }] 
       (if (not= variant "current") expected
         (map #(character %1 %2) expected
              (map #(get-variant expected actual %) index))))))




(comment 

(def expected ["h" "e" "l" "l" "o"])
(def actual [""])
(word expected actual "current")

(def l 
  ["white" "white" "white" "white" "white" "white" "white" "white" "white" "black" "black"])

(doseq [[i word] (map-indexed list l)] (println i word))
(into [:span {:style (merge word-css {:color "#fff"})}]
(map #(character %1 %2) expected 
     (map #(get-variant expected actual %) (range (count expected))))
)

(range (count (seq expected)))
(count expected)

)
