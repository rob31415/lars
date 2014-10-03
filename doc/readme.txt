autor: rob
date: 1.5.2013


__________
what?

this is a demo app of of a web-application, developed with scala and vaadin- using the mvp pattern.
it's using mybatis as db-connection sort of orm helper.


__________
why?

it is inspired
1. by the desire to clearly seperate business logic from mvc, as usually in mvc web-apps the business logic of a complex app is buried somewhere in the controllers and models,
2. the hatred of html, javascript and the likes.

with this method a developer can avoid those things (except for styling, which is done via css in vaadin).
vaadin is like swing or any other gui-toolkit, but it's output is being sent to a browser. that means a developer can write (similar to gwt) pure scala (or any other jvm-language) code and have a application architecture just like if it would have been developed for a desktop, rather then the web.
thus, apps developed in this fashion are not like usual page-based webapps - with reloads when changing pages and stuff like that. it feels like a usual oldschool desktop-app (no tablet- or smartphone app) - with tables, windows, menus and all that and is just accessible by browser.


__________
directory structure


src/
the whole shebang


src/java/
java-code goes here - we don't have any for this project


resources/
logger-config (log4j for scala), mybatis-config, mybatis-migrations and scaffolding


resources/scaffolding
if a new mvp-triad is being introduced, there is a howto and a code-blueprint to do this task efficiently.


resources/mybatis/mappings
containing the sql statements for database operations and the mybatis-xml that works together with the daos.
under the hood, mybatis creates objects in the namespace lars.model.mybatis.mapper. these are being used directly by their appropriate and similar-named daos. so it goes like this:
app -> dao -> mybatis-object (described by xml) -> sql -> db.


resources/mybatis/migrations
we got some scripts setting up the database. watch out, migrating down does not prevent data-loss.


src/test/
contains unit tests for specs2. in sbt enter "test". that's currently broken.


src/main/
self-explanatory


src/main/scala/lars/
there are model/, view/ and presenter/ directories containing scala code.


app/global/ 
things that must be derived from to stick to the conventions for this app and singletons, both app-local and global (see scopes).


app/global/Editlock.scala
note: a dto (see below for what a dto is) is identified by it's id.
if two users, A and B are singed on and A has a dto - say a location - that she's currently editing, we put that dto into an editlock-set. the editlock is global, not per user, but totally global (see scopes) and accessible by every signed-on user. if B wants to edit it, she get's a message, that it's currently edited by someone else.
we should extend this by saying to B, that A is editing it and perhaps even giving B the possibility to say to A, that she wants to edit it too (and A should hurry the hell up).


model/dto/
we use data-transfer-objects to pass data - mostly from the database - around.
here's the cycle:
first, get them from the db or create them new (new ones have id -1). then display them in the view. the view-data gets modified by the user. the data then is put back from the ui-elements into the dto. dto goes to dao. dao stores them away into the db. "Fights begin, finger prints are took, days is lost, bail is made, court dates are ignored, cycle is repeated." (quote from squidbillies)

important: a dto is identified by it's id.


view/
contains vaadin code.
sometimes, we use a vaadin-factory that automatically maps db fields to ui-elements and fills them with the data in the dto. so it goes:
dto -> goes to view -> view uses vaadin-factory -> factory creates ui-elements (according to datatype) & puts in data from dto


model/service/dao
we use data-access-objects as an interface-layer between app and database.
with them, dtos can be saved and retrieved - the latter either as single object or as 


__________
scopes
we use different scopes.
there is application-local
that means global per user - another user can't see or access that data. To see how this is done, read app/global/Applocal.scala.
e.g. the publisher-subscribe mechanism (Broadcaster.scala) is app-local and thus almost all events that are broadcasted affect only the distinct user, not another.

there is global
all users can access that data. it's done with the help of the scala object keyword, which makes something a singleton.
e.g. the Editlock.scala is global.


__________
events

in each directory model/, view/ and presenter/ there are event/ directories, containing events, which are used like messages.
events are send by the lars.global.Broadcaster throughout the whole application and whoever wants to react to something can do so.

there is a scala-idiom used to implement a publisher-subscriber system for senders and receivers of events.
they are wired up at app-start: see lars.presenter.Main.wire_up. you'll see that in the mvp-way the presenter gets direct aggreagtions of it's own view and model and - if neccessary - references to foreign models. the latter is because it might want to react from messages from there - for example the user-view might want to update itself when the location for the user (or anything in the location-table of the databse) was changed.

for now, this is only working per user. if two users are signed in and for example user A changes the location-table we'd need database triggers in order to make the location-dao from user B emit an event that leads to user B's gui (location and user window) to be refreshed.

to get around this - for now - we use an editlock - see app/global/Editlock.scala.

dependency injection might be an option if this becomes to complex - that would chance the structure partially and the start up of the application.

the events are seperated by the ejb-container (here jetty) per user (per ejb-app), so on user can not send an event to another user.


__________
data-history

one requirement this app is meeting is, that we want to retrieve data how it was at a certain time.
this is very tricky for n:n and that's why we have two stored-procedures.


__________
relations

in this app there is an example for a 1:n mapping (between role and user) and for a n:n mapping (bewteen location and user).
for n:n there is a convenient and generalised way to implementation: n_to_m_join.xml and an accompanying N_to_m_join.scala. for n:n we use join-tables.

see dao/Location.scala and dao/User.scala to get the idea of how to store a new location and an accompanying new entry in the join-table (same for new user and join-table).

