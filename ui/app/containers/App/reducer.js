import { fromJS } from 'immutable';

import {
  LOAD_BOOKS,
  LOAD_BOOKS_SUCCESS,
  LOAD_BOOKS_ERROR,
} from './constants';

// The initial state of the App
const initialState = fromJS({
  loading: false,
  error: false,
  books: false,
});

function appReducer(state = initialState, action) {
  switch (action.type) {
    case LOAD_BOOKS:
      return state
        .set('loading', true)
        .set('error', false)
        .set('books', false);
    case LOAD_BOOKS_SUCCESS:
      return state
        .set('books', action.books)
        .set('loading', false);
    case LOAD_BOOKS_ERROR:
      return state
        .set('error', action.error)
        .set('loading', false);
    default:
      return state;
  }
}

export default appReducer;
