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
   [typing.components.word :refer [word]]
   [clojure.string :as str]))


; (set! *warn-on-infer* true)
; 
; (defn event-value
;   [^js/Event e]
;   (.. e -target -value))

(defn  multiply [a b]
  (* a b))

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


(defn end-timer []
  (swap! 
    app-state
    assoc :typing-state (typing-state "awaiting")))


;; ???
(defn run-timer []
  (defn set-timeout []
    (let [time- (@app-state :time)]
      (if (< time- 60) (do
                         (swap! app-state update-in [:time] inc)
                         (run-timer))
        (end-timer)))) 100)



(defn get-app-element []
  (gdom/getElement "app"))


(defn hello-world []
  [:div
   [:h1 (:typing-state @app-state)]
   [:h3 "Edit this in src/typing/core.cljs and watch it change!"]])


;; what a messs....
(defn on-key-down [e]
  (let [input-words (:input-words @app-state)
        i (:i @app-state)]
    (cond (= [:key e] "backspace")
          (if (= (count (nth input-words i)) 0) ;or e.ctrlkey
            (do
              (when (> i 0) (dec i))
              (reset! (nth input-words i)
                      (subs (nth input-words i) 0 (inc i))))
            (reset! (nth input-words i)
                    (butlast (nth input-words i))))
          (= [:key e] " ")
          (do
            (swap! i (inc i))
            (reset! (nth input-words i) "")
            ; getcpm
            (when (= (:typing-state @app-state) "awaiting")
              (swap!  app-state
                     assoc :typing-state (typing-state "finished"))))
          (= (count [:key e]) 1)
          (conj (nth input-words i) [e.key])
          ; if state not started set state -> in-prog
          ; run timer
          :else nil) 
    (print input-words i)))


; use as test app-state
(def state (r/atom
             {:input-words ["hello" "wor  "]
              :i 3
              :time 12
              :typing-state
              (typing-state "not-started")}))

; gloaal test data
(def expected ["hello" "world" "this" "is" "not" "a" "Test"])
(def actual ["hello" "world" "th " "" "" "" "" "" "" "" ""])
(count actual)

(def s '("done" "done" "done" "done" "done" "done" "current"))

(defn words []
(let [idx (range (count expected))] ; FIX to include real data
         (into [:div ] (map
           #(word (nth expected %2) (or (get actual %2) %2) %1) s         
           idx))))
; (map #(get-word-type % (dec (count expected))) idx)
(map #(get-word-type % (dec (count expected))) (range (count expected)))

(dec (count expected))

(words)


(defn test-span-tag []
  (word expected actual "done")
  )


; (def did-mount {:component-did-mount #(.focus (rdom/dom-node %)))

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
      [words]]
      ]]
    [paper {:style {:padding "0px"}}
     [text-field
      {:input-ref @input-ref!
       :placeholder "start typing here"
       :margin "none"
       :full-width "true"
       :variant "outlined"
       :value current
       :onkeydown "fn on-key-down"}]]]
   ])))



(comment
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
