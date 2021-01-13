(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.components.style :as style]
   [reagent-material-ui.colors :as mui-colors]
   [reagent-material-ui.core.text-field :refer [text-field]]
   [reagent-material-ui.core.paper :refer [paper]]
   [reagent-material-ui.styles :as mui-styles]
   [reagent-material-ui.core.icon-button :refer [icon-button]]
   [typing.components.word :refer [word]]
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

(defn randint
  [start end]
  (+ start (int (* end (rand)))))

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

(def cpm 0)

(defonce app-state (r/atom
             {:input-words ["hello" "wor  "]
              :i 0
              :time 12
              :typing-state
              (typing-state "not-started")}))


; characters is wrong, doesnt include spaces
; characters should handle text better
(defn get-cpm
  [input-words]
  (let [time-elapsed (@app-state :time)
        characters (reduce + (map count input-words))]
    (/ (Math/floor (* characters 60)) time-elapsed)))


; (defn end-timer []
;   (swap! 
;     app-state
;     assoc :typing-state (typing-state "awaiting")))


;; ???
; (defn run-timer []
;   (defn set-timeout []
;     (let [time- (@app-state :time)]
;       (if (< time- 60) (do
;                          (swap! app-state update-in [:time] inc)
;                          (run-timer))
;         (end-timer)))) 100)
; 


;; what a messs....
(defn on-key-down [e]
  (let [input-words (:input-words @app-state)
        i (:i @app-state)]
    (cond (= (.-key e) "backspace")
          (if (= (count (nth input-words i)) 0) ;or e.ctrlkey
            (do
              (when (> i 0) (dec i))
              (reset! (nth input-words i)
                      (subs (nth input-words i) 0 (inc i))))
            (reset! (nth input-words i)
                    (butlast (nth input-words i))))
          (= (.-key e) " ")
          (do
            (swap! i (inc i))
            (reset! (nth input-words i) "")
            ; getcpm
            (when (= (:typing-state @app-state) "awaiting")
              (swap!  app-state
                     assoc :typing-state (typing-state "finished"))))
          (= (count (.-key e)) 1)
          (conj (nth input-words i) (.-key e))
          ; if state not started set state -> in-prog
          ; run timer
          :else nil) 
    (print input-words i)))



; gloaal test data
(def expected ["hello" "world" "this" "is" "not" "a" "Test"])
(def s '("done" "done" "done" "done" "done" "done" "current"))
(def actual ["hello" "world" "th " "" "" "" "" "" "" "" ""])
(count actual)

(defn words []
(let [idx (range (count expected))] ; FIX to include real data
         (into [:div ] (map
           #(word (nth expected %2) (or (get actual %2) %2) %1) s         
           idx))))


(doseq [[i word] (map-indexed vector words)] (println i word))


; (map #(get-word-type % (dec (count expected))) idx)
(map #(get-word-type % (dec (count expected))) (range (count expected)))

(dec (count expected))

(comment 
(words)
)

(def text (str/split "In today’s technology landscape, the web is king. Web apps are everywhere, and the lingua franca of the web is JavaScript. Whether the task is adding interactivity to a simple web page, creating a complex single-page application, or even writing a server-side application, JavaScript is the defacto tool. Despite its age and its hasty design, JavaScript has evolved to power an entire generation of web development. The JavaScript community is also one of the most active and prolific software development communities ever, with libraries and frameworks for any conceivable use." #""))


; render text first and only update class on input :)
(defn render-text [text]
  (into [:div ]
        (for [character text]
          [:span {:class ""
                  :style
                  {:font-size 16
                   :padding ".1em 0"
                   :border-radius 5
                   }} character])))

(def input-ref "words at the flake") ; input from text-area
(defn character-state! []
  (let [toggle-state (r/atom false)
        correct (r/atom true)]
    (for [character text
          input (str/split input-ref #"")]
      (cond (nil? input) (@swap! correct false)
            ; change characters span class to true
            (= character input) "correct" 
            ; change characters span class to false
            ; swap correct false
            :else  "false" 
    ))))

; (fn [] (.addEventListener js/document "input" handler))

; (def did-mount {:component-did-mount #(.focus (rdom/dom-node %)))

(defn text-area [input-ref!]
[:input
      {
       ; :style {:position "fixed" :left "-100%" :top "50%"}
       :type "text"
       ; :placeholder "start typing here"
       :margin "none"
       :full-width "true"
       :variant "outlined"
       :default-value @input-ref!
       :on-change #(reset! input-ref! (-> % .-target .-value))
       }])

(defn app [] 
  (let [input-ref! (r/atom nil)
        words-ref! (r/atom nil)
        current (nth (:input-words @app-state) (:i @app-state))]

    (fn []
  [:div {:style style/root}
   [:div {:style style/container}
    [paper {:elevation "4"
             :style style/board}
     [paper {:elevation "2"}
      [:div {:style style/statistics}
       [:div "timer: " ; FIX 
        (if (= (@app-state :typing-state) "awaiting") "finsh word"
          ) ]
       [:div {:style
              {:margin-left "20"}}
        "CPM: " (if
                  (or (= @cpm nil) (= @cpm Math/Inf))
                  "?"
                  @cpm)]
       [:div {:style
              {:flex-grow "1"}}]
       [icon-button {:color "inherit" 
                      :onclick " reset fn"}
        [:i {:class "material-icons"} "replay" ]]]]
     [:div {:style {:padding "35px 15px"}}

      [:div {:style style/inputs}

      [words]
      [render-text text]
      [:div @input-ref!]
  
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
(word expected (:input-words @app-state) "awaiting")

(:input-words @app-state)
(nth (:input-words @state) (:i @state))
(:input-words @app-state)


)


(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

