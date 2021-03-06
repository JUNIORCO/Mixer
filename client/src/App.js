import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "./views/Home";
import Login from './views/Login';
import Quiz from './views/Quiz';
import Search from './views/Search';
import Header from './components/Header';

const App = () => {
  const sections = [
    { title: 'Quiz', url: '/quiz' },
    { title: 'Search', url: '/search' },
    { title: 'Login', url: '/login' },
  ];

  return (
    <div className="App">
      <Header title="Mixer" sections={sections} />
      <Switch>
        <Route exact path="/"><Home /></Route>
        <Route path="/quiz"><Quiz /></Route>
        <Route path="/search"><Search /></Route>
        <Route path="/login" component={Login}></Route>
      </Switch >
    </div >
  );
}

export default App;
