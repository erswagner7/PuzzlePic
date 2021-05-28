# PuzzlePic
IT3048C Mobile App Development

## Introduction - Erich

## Storyboard - Michael McQ

## Functional Requirements - Matthew Dumford

### Requirement 1
As a user, I want to be able to turn my own photo into a slide puzzle.
#### Example 1
**Given**: Saved photo(s) on the user's device. 

**When**: The user selects a photo within our app. 

**Then**: Our app turns the photo into a slide puzzle and shuffles it. 

#### Example 2
**Given**: The user's device has a functioning camera. 

**When**: The user takes a photo through our app. 

**Then**: Our app turns the photo into a slide puzzle and shuffles it. 

#### Example 3
**Given**: The user can select or take a photo through our app. 

**When**: The user cancels the photo selection. 

**Then**: No slide puzzle will be generated. 

### Requirement 2
As a user, I want to be able generate a random slide puzzle from an image the app chooses.
#### Example 1
**Given**: The user's device is connected to WiFi. 

**When**: The user chooses to generate a random puzzle. 

**Then**: Our app downloads a picture from an online database, generates a slide puzzle, and shuffles it. 

#### Example 2
**Given**: The user's device is connected to cellular data but NOT WiFi. 

**When**: The user chooses to generate a random puzzle. 

**Then**: Our app warns the user of potential cellular data usage and waits for confirmation or cancellation. 

#### Example 3
**Given**: The user's device has no internet connection. 

**When**: The user chooses to generate a random puzzle. 

**Then**: Our app alerts the user of the lack of internet connectivity and offers to generate a puzzle from the device's local photos. 

### Requirement 3
As a user, I want to be able to save puzzles to solve again at another time.
#### Example 1
**Given**: The user has signed into our app. 

**When**: The user chooses to save a puzzle. 

**Then**: Our app saves the puzzle in its current state to a database tied to the user's account. 

#### Example 2
**Given**: The user has not signed into our app. 

**When**: The user chooses to save a puzzle. 

**Then**: Our app prompts the user to create an account or sign in to an existing account. 

#### Example 3
**Given**: The user is signed in AND has puzzle(s) already saved. 

**When**: The user chooses to recall a previously saved puzzle. 

**Then**: The puzzle is loaded from the database. 

#### Example 4
**Given**: The user is signed in AND has NO puzzles saved. 

**When**: The user chooses to recall a previously saved puzzle. 

**Then**: Our app alerts the user that they have no saved puzzles and does not load anything from the database.

#### Example 5
**Given**: The user is not signed in. 

**When**: The user chooses to recall a previously saved puzzle. 

**Then**: Our app prompts the user to create an account or sign in to an existing account. 

### Requirement 4
As a user, I want to be able to reshuffle a slide puzzle, whether or not it has been solved.
#### Example 1
**Given**: A slide puzzle is being generated. 

**When**: Our app shuffles the puzzle. 

**Then**: Our app verifies that the puzzle can be solved. This can be done by the shuffling function or by a separate verification function. 

#### Example 2
**Given**: The user has a puzzle already generated AND has NOT solved it. 

**When**: The user chooses to shuffle the puzzle. 

**Then**: Our app shuffles the puzzle tiles again. 

#### Example 3
**Given**: The user has a puzzle already generated AND has solved it. 

**When**: The user chooses to shuffle the puzzle. 

**Then**: Our app alerts the user that the puzzle is solved, and waits for confirmation or cancellation. 

#### Example 4
**Given**: The user has a puzzle already generated AND the user's device has an accelerometer.

**When**: The user shakes their device. 

**Then**: Our app asks if the user wanted to shuffle the puzzle, and waits for confirmation or cancellation before shuffling according to examples 2 and 3. 

## Class Diagram - Andre

## Team Members and Roles
### UI Specialist - Matthew Saling | Matthew Dumford
### Integration Specialist - Andre A. | Michael McQuade
### DevOps and Product Owner - Erich Wagner
