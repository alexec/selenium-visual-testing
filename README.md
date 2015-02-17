Visual Testing With Docker And Selenium
===
Visual Testing is a way to test a web site to make sure that it looks as you'd expect in different browsers. I've come across an interesting way to do simple Visual Testing without having access to a full grid.

In this tutorial we will:

* Set-up a small Selenium Grid, hosted on your desktop using Docker.
* Create a JUnit runner that injects the appropriate web driver, and runs the test in each browsers, saving screenshots when requested.