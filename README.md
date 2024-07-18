# Song-Prediction
MOTIVATION:
  This project was inspired by the "Go to Radio" function in Spotify. A user will enter the index of a song in the command line.
  The program will then create a radio station with similar songs to the song chosen by the user.

TASKS:
  The program will require 5 arguments; song titles, song ratings, output file, the song index, and true for "shuffle" or false for "in order". The radio station will contain 
the 50 most similar songs to the user's chosen song. Once the radio station is built, the program will output a file containing the list of songs. If the user chooses "in order", 
the radio station will be presented in order from most related to least related songs. If "shuffle" is chosen, the songs will be presented in a random order. 

  The songs and ratings files must contain at least 100 songs, for these files are representing a database of songs that the user is choosing from. Any files smaller than this 
will produce an error. 

TO RUN:
  gradle run -q --args="'input_files/songFileName' 'input_files/ratingsFileName' 'output_files/songOut' K songIndex Shuffle? (true/false)"
