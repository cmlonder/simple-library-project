import { fromJS } from 'immutable';

import { GET_BOOKS } from './constants';

const initialState = fromJS({
  booksLoaded: ''
});

function homeReducer(state = initialState, action) {
  switch (action.type) {
    case GET_BOOKS:
      return state.set('booksLoaded', true);
    default:
      return state;
  }
}

export default homeReducer;
