export default (previousState = 0, { type, payload }) => {
  if (type === "BITCOIN_RATE_RECEIVED") {
    return payload.rate;
  }
  return previousState;
};
