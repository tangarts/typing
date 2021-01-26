(ns typing.components.dialog
  (:require
    [typing.components.percentile :as percentile]
    ))


(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])

(def percentageBar {
  :min-width 360
  :width "100%"
  :height "22"
  :background "#ddd"
  :position "relative"
  :overflow "hidden"
  :borderRadius "20"
  })

(defn complete-percentage-bar
  [percentage is-error?]
 {:width (str percentage "%")
  :height "100%"
  :background (if is-error? "#fd3c01" "#009688")
  :borderRadius 20
  :position "absolute" })


;interface ResultDialogProps {
;  open: boolean;
;  closeDialog: (event: React.MouseEvent<HTMLElement>) => void;
;  expected: string[];
;  actual: string[];
;}

;const ResultDialog = ({
;  open,
;  closeDialog,
;  expected,
;  actual
;}: ResultDialogProps) => {
;  if (!open) return <div />;
;
;  const mistakes: { actual: string; expected: string }[] = actual
;    .map((actual, i) => ({ actual, expected: expected[i] }))
;    .filter(({ expected, actual }) => expected !== actual && actual !== "");

;  const cpm = actual.map(word => word.length).reduce((a, b) => a + b + 1, 0);
;  const cpmCorrected = actual.filter((actual, i) => actual === expected[i]).map(word => word.length).reduce((a, b) => a + b + 1, 0);
;
;  console.log(`${cpm} -> ${cpmCorrected}`);
;
;  const percentile = getPopulationSpeedPercentile(cpm);
;  const percentileCorrected = getPopulationSpeedPercentile(cpmCorrected);
;
;  console.log(`${percentile} -> ${percentileCorrected}`);
;
;  return (
;    <Dialog open={open}>
;      <DialogTitle style={{ textAlign: "center" }}>{cpm} CPM</DialogTitle>
;      <DialogContent>
;        <DialogContentText>
;          Your score beats or equals {percentile}% of all
;        </DialogContentText>
;        <div {...percentageBar}>
;          <div
;            {...completePercentageBar(percentileCorrected)}
;            style={{ zIndex: 2 }}
;          />
;          <div
;            {...completePercentageBar(percentile, true)}
;            style={{ zIndex: 1 }}
;          />
;        </div>
;        <DialogContentText>
;          {mistakes.length === 0
;            ? "Congrats! You have made no mistakes! Keep it up!"
;            : `You have made ${mistakes.length} mistake${
;                mistakes.length > 1 ? "s" : ""
;              }:`}
;        </DialogContentText>
;        {mistakes.length > 0 && (
;          <ul>
;            {mistakes.map((m, i) => (
;              <li key={i}>
;                <DialogContentText>
;                  You typed "{m.actual}" instead of "{m.expected}"
;                </DialogContentText>
;              </li>
;            ))}
;          </ul>
;        )}
;      </DialogContent>
;      <DialogActions>
;        <Button color="primary" variant="contained" onClick={closeDialog}>
;          OK
;        </Button>
;      </DialogActions>
;    </Dialog>
;  );
;};
