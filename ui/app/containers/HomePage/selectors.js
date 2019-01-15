import { createSelector } from 'reselect';

const selectHome = (state) => state.get('home');

const makeSelectAddedBook = () => createSelector(
  selectHome,
  (homeState) => homeState.get('addedBook')
);

export {
  selectHome,
  makeSelectAddedBook,
};
