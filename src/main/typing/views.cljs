(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.components.style :as style]
   [reagent-material-ui.core.paper :refer [paper]]
   [typing.components.word :refer [character-]]
   [typing.components.timer :refer
    [controls-view timer-component]]
   [typing.components.text :refer
    [road-not-taken-1 ex-1 voltaire yeats meditations-2-1 e-to-a seneca]]
   [clojure.string :as string]))

(defn random-text []
  (rand-nth [road-not-taken-1 ex-1 voltaire yeats e-to-a seneca]))

(def app-state
  (r/atom {:input nil :text (random-text) }))

(defn default-state []
  "Generates a map with start/end time keys"
  (let [now (.now js/Date)]
    {:stime now
     :etime nil
     :on? false}))


(def state (r/atom (default-state)))




(defn get-word-type [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))


(defn render-text [text actual]
  (into [:div ]
        (for [[i c] (map-indexed vector text)]
          ^{:key i}
          [character-
           c
           (get @actual i)
           (get-word-type i (count @actual))])))


(defn strip-text [text] 
  (string/split text #""))

(defn text-area [input-ref!]
  (fn []
    [:input#input
     {:style {:position "fixed" :left "-100%" :top "50%"} ; hidden
      :type "text"
      :auto-focus "true"
      :value @input-ref!
      :on-change #(reset! input-ref! (-> % .-target .-value))
      ;:on-key-down (fn [e] (.log js/console (.-key e)))
      }]))

(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

(defn finished?
  [text input-ref!]
  (when (>= (count @input-ref!) (count @text))
    [:p "Done"]))

(defn app [] 
  (let [input-ref! (r/atom nil)
        text (r/atom (strip-text (random-text))) ]
    (fn []
      [:div {:style style/root
             :on-click #(-> js/document
                            (.getElementById "input") (.focus))}
       [:div {:style style/container} 
        [paper {:elevation "4" :style style/board}
         [paper {:elevation "2"}
          [:div {:style style/statistics}

           ; [timer-component]
           [:div {:style {:margin-left "20px"}}
            " CPM: " ] ; fix

           [:div {:style {:flex-grow "1"}}]

           [controls-view]
           [:button {:on-click
                     #((reset! text (strip-text (random-text)))
                       (reset! input-ref! nil))}
            "replay"]]]
         [:div {:style {:padding "35px 25px"}}
          [:div {:style style/inputs}
           [text-area input-ref!] ; hidden text-area

           [render-text @text (r/atom (string/split @input-ref! #""))]

           [finished? text input-ref!]
           ]]]]])))




(comment 
  (for [i (range (count text))] (get-word-type i (count @input-ref)))
  (for
    [[i word] (map-indexed vector text)]
    [:span {:class ""
            :style word-css} character])

  (map = (string/split @input-ref! #"") (subvec text 0 (count @input-ref!)))

  ; (fn [] (.addEventListener js/document "input" handler))

; (defn render-line [expected actual]
;   (let  []
;     (into [:div ]
;           (for [[i character] (map-indexed vector expected)]
;             ^{:key i}
;             (word character
;                   (get @actual i nil)
;                   (get-word-type i (count @actual)))))))
; 
; (-> 'typing.components.text ns-interns keys rand-nth )

)

