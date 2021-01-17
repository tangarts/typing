(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.components.style :as style]
   [reagent-material-ui.core.paper :refer [paper]]
   [typing.components.word :refer [word character]]
   [typing.components.text :as txt]
   [clojure.string :as str]))


; (set! *warn-on-infer* true)
; 
; (defn event-value
;   [^js/Event e]
;   (.. e -target -value))

(defn timer-component []
  (let [seconds-elapsed (r/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 1000)
      [:div
       "Seconds Elapsed: " @seconds-elapsed])))


(defn get-word-type 
  [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))


(defn render-line [expected actual]
  (let  []
    (into [:div ]
          (for [[i character] (map-indexed vector expected)]
            ^{:key i}
            (word character
                  (get @actual i nil)
                  (get-word-type i (count @actual)))))))



(def expected ["h" "e" "l" "l" "o"])
(def actual [])
(word expected actual "current")


(defn render-text [text input-ref!]
  (into [:div ]
        (for [[i c] (map-indexed vector text)]
          ^{:key i}
          [character c "black"])))




; {:class ["" (when (< i (count @input-ref!)) "correct") ""] :style style/word-css }
; (get (second [:span {:class "correct"  :style style/word-css } "hello"]) :class)


; :background "#3bd376"


(def text
  (str/split "No matter what you're doing, the most essential thing is to not give up. Fail as many times as it takes. Keep trying persistently until you can call yourself average. If you can collect a nice group of average-level skills, that's already above-average. You've created your own sort of talent." #"")) 
 
; (def long-text
;   (str/split "There was no vegetation of any kind on that broad expanse, but only a fine grey dust or ash which no wind seemed ever to blow about. The trees near it were sickly and stunted, and many dead trunks stood or lay rotting at the rim. Even the long, dark woodland climb beyond seemed welcome in contrast. There had been no house or ruin near; even in the old days the place must have been lonely and remote. And at twilight, dreading to repass that ominous spot, I walked circuitously back to the town." #""))


(defn text-area [input-ref!]
  (fn []
    [:input#input {
                   :style {:position "fixed" :left "-100%" :top "50%"} ; hidden
                   :type "text"
                   :auto-focus "true"
                   :default-value @input-ref!
                   :on-change #(reset! input-ref!
                                       (-> % .-target .-value))}]))

(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

(declare input-ref!)

(defn finished?
  [text input-ref!]
  [:div (when (>= (count @input-ref!) (count text))
          "Done!")])

(defn app [] 
  (let [input-ref! (r/atom nil)]

    (fn []
      [:div {:style style/root
             :on-click #(-> js/document
                            (.getElementById "input") (.focus))}
       [:div {:style style/container} 
        [paper {:elevation "4" :style style/board}
         [paper {:elevation "2"}
          [:div {:style style/statistics}
           [:div "timer: " ; FIX 

            ]
           [:div {:style {:margin-left "20"}}
            " CPM: " ; Fix
            ]
           [:div {:style
                  {:flex-grow "1"}}]
           [:a {:color "inherit"
                :onclick " reset fn"}
            "replay"]]]
         [:div {:style {:padding "35px 15px"}}
          [:div {:style style/inputs}
           [text-area input-ref!] ; text-area
           [render-line text (r/atom (str/split @input-ref! #""))]
           [finished? text input-ref!]

           ]]]]])))



(comment 
  (for [i (range (count text))] (get-word-type i (count @input-ref)))
  (for
    [[i word] (map-indexed vector text)]
    [:span {:class ""
            :style word-css} character])

  (map = (str/split @input-ref! #"") (subvec text 0 (count @input-ref!)))

  ; (fn [] (.addEventListener js/document "input" handler))
  ; (def did-mount {:component-did-mount #(.focus (rdom/dom-node %)))
  )

