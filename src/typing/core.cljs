(ns ^:figwheel-hooks typing.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [typing.components.style :as style]
   [reagent-material-ui.colors :as mui-colors]
   [reagent-material-ui.core.text-field :refer [text-field]]
   [reagent-material-ui.core.paper :refer [paper]]
   [reagent-material-ui.styles :as mui-styles]
   [reagent-material-ui.core.icon-button :refer [icon-button]]
   [typing.components.word :refer [word word-css]]
   [clojure.string :as str]))


; (set! *warn-on-infer* true)
; 
; (defn event-value
;   [^js/Event e]
;   (.. e -target -value))


(defn get-word-type 
  [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))

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


; characters is wrong, doesnt include spaces
; characters should handle text better
(defn get-cpm
  [input-words]
  (let [time-elapsed (@app-state :time)
        characters (reduce + (map count input-words))]
    (/ (Math/floor (* characters 60)) time-elapsed)))


(defn get-app-element []
  (gdom/getElement "app"))



(defn words [expected actual]
  (let [idx (range (count expected))
        variant (for [i (range (count expected))]
                  (get-word-type i (count @actual)))
        ] 
    (into [:div ]
          (map #(word (nth expected %2) 
                      (get @actual %2) %1) variant idx))))


; (map #(get-word-type % (dec (count expected))) idx)

(def text
  (str/split
    "In today's technology landscape, the web is king.
    The JavaScript community is also one of the most active and prolific software development
    communities ever, with libraries and frameworks for any conceivable use." #"")) 



(comment 
  (for [i (range (count text))] (get-word-type i (count @input-ref)))

  (doseq
    [[i word] (map-indexed vector text)]
    [:span {:class ""
                    :style word-css} character])

  (map = (str/split @input-ref! #"") (subvec text 0 (count @input-ref!)))

)

(defn check-correct [input! text i]
  (when (<= (count @input!) i) 
    (= (last @input!) (nth text i))))

; render text first and only update class on input :)

(defn render-text [text input!]
  (let [style-class (r/atom "")]
    (fn [text input!]
    (into [:div#text ]
          (for [[i character] (map-indexed vector text)]
            [:span {:class (if (check-correct input! text i) "correct" "")
                    :style word-css} character])))))


; (if (check-correct input! text) "correct" "incorrect")

; input and text body
; incorrect: input and substring of text not equal in length to input are equal
; corrent input and substring of text equal in length to input are equal 
; current length on input


; (fn [] (.addEventListener js/document "input" handler))

; (def did-mount {:component-did-mount #(.focus (rdom/dom-node %)))

(defn text-area [input-ref!]
  [:input#input {
           ; :style {:position "fixed" :left "-100%" :top "50%"} ; hidden
           :type "text"
           :margin "none"
           :full-width "true"
           :variant "outlined"
           :default-value @input-ref!
           :on-change #(reset! input-ref! (-> % .-target .-value))}])

(def input-ref! (r/atom nil))

(defn app [] 
  (let [;input-ref! (r/atom nil)
        words-ref! (r/atom nil)
        current (nth @input-ref! (:i @app-state))]
    (fn []
      [:div {:style style/root}
       [:div {:style style/container}
        [paper {:elevation "4" :style style/board}
         [paper {:elevation "2"}
          [:div {:style style/statistics}
           [:div "timer: " ; FIX 
            (if (= (@app-state :typing-state) "awaiting") "finsh word")
            ]
           [:div {:style
                  {:margin-left "20"}}
            "CPM: " (if (or (= @cpm nil) (= @cpm Math/Inf)) "?" @cpm)]
           [:div {:style
                  {:flex-grow "1"}}]
           [icon-button {:color "inherit" 
                         :onclick " reset fn"}
            [:i {:class "material-icons"} "replay" ]]]]
         [:div {:style {:padding "35px 15px"}}

          [:div {:style style/inputs}

           [render-text text input-ref!]
           [:div @input-ref!]
           [words text (r/atom (str/split @input-ref! #""))]
           ]]]
        [paper {:style {:padding "0px"}}
         ; text-area
         [text-area input-ref!]
         ]]
       ]
      )))


(comment

  [:div.timer#timer]
  [:div.container
   [:div.quote-display#quote-display]
   [:textarea.quote-input#quote-input]
   ]
  quote-display-element.event-listener('input',)
  quote-input-element
  timer-element



(def actual "if by she go give you enve ou ")
(def expected "if by she go give you even our")



)


(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])



(defn mount [el]
  (rdom/render
    [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
