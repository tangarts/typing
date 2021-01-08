(ns typing.components.style)


(def root
  {
   :font-family "sans-serif"
   :width "100%"
   :height "100%"
   :min-height "100vh"
   :background "#eaf0f7"
   :display "flex"
   :flex-direction "column"
   :justify-content "center"
   :align-items "center"
   })


(def container
  {
   :margin "10px auto"
   :max-width "750"
   :width "90%"
   })

(def board { :margin-bottom "15px" :overflow "hidden" })

(def statistics {
  :padding "8px 20px"
  :display "flex"
  :flex-direction "row"
  :background "#444"
  :color "white"
  :align-items "center"
  :font-size "13"
})

(def inputs {
  :user-select "none"
  :overflow "hidden"
  :text-align "center"
  :height "180"
})
