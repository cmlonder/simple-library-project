import { call, put, select, takeLatest } from 'redux-saga/effects';
import { LOAD_BOOKS } from 'containers/App/constants';
import { booksLoaded, booksLoadingError } from 'containers/App/actions';
import { makeSelectAddedBook } from 'containers/HomePage/selectors';

import request from 'utils/request';

export function* getBooks() {
  const requestURL = 'https://git.heroku.com/library-server-demo.git/api/books';

  try {
    // Call our request helper (see 'utils/request')
    const books = yield call(request, requestURL);
    yield put(booksLoaded(books));
  } catch (err) {
    yield put(booksLoadingError(err));
  }
}

export function* addBook() {
  const requestURL = 'http://localhost:8080/api/books';
  const addedBook = yield select(makeSelectAddedBook());
  try {
    // Call our request helper (see 'utils/request')
    const books = yield call(request, requestURL, {
      method: 'POST', // or 'PUT'
      body: JSON.stringify(addedBook),
      headers: {
        'Content-Type': 'application/json'
      }
    });
    yield put(booksLoaded(books));
  } catch (err) {
    yield put(booksLoadingError(err));
  }
}

export default function* booksData() {
  yield takeLatest(LOAD_BOOKS, getBooks);
}
