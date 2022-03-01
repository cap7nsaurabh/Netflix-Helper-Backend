<h2>Netflix Helper</h2>
<p>It is a backend repository for the project Netflix helper. It is a CRUD application project that utilizes REST archtitecture to provide data to the <a href="https://github.com/cap7nsaurabh/Netflix-Helper-Frontend"><span>front end</span></a> which is implemented using Springboot. It uses important concept like One to many and many to many relationship between enitities.</p>
<h3>Technologies</h3>
<ul>
  <li>Java</li>
  <li>Maven</li>
  <li>Spring Boot</li>
  <li>JPA</li>
  <li>MySQL</li>
</ul>
<h3>Endpoints</h3
<ul>
  <li>User Controller
    <br>
  <ol>
   <li>/user: used to get an user</li>
    <li>/register: used to register an user</li>
    <li>/login: used to authenticate an user</li>
  </ol></li>
  <li>Favourite Controller: Endpoints under this are used to add and modify favourite lists for an user
    <ol>
      <li>/{userid} Get Method : Returns a list of all favourites for given user</li>
      <li>/{userid} Post Method : adds movie to the list of all favourites for given user</li>
      <li>/{userid}/{showid} Delete Method : Deletes given movie from the list of all favourites for given user</li>
    </ol></li>
  <li>WatchLater Controller: Endpoints under this are used to add and modify Watch later lists for an user
   <ol>
      <li>/{userid} Get Method : Returns a list of all watch-later for given user</li>
      <li>/{userid} Post Method : adds movie to the list of all watch-later for given user</li>
      <li>/{userid}/{showid} Delete Method : Deletes given movie from the list of all watch-later for given user</li>
    </ol>
  </li>
</ul>
