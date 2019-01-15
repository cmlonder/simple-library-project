import { createSelector } from 'reselect';

const selectGlobal = (state) => state.get('global');

const selectRoute = (state) => state.get('route');

const makeSelectLoading = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('loading')
);

const makeSelectError = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('error')
);

const makeSelectBooks = () => createSelector(
  selectGlobal,
  (globalState) => globalState.get('books')
);


export {
  selectGlobal,
  selectRoute,
  makeSelectBooks,
  makeSelectLoading,
  makeSelectError,
};
