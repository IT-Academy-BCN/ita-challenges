# Title: [CHALLENGE] Agree on the information saved in a Submission (student answer)

## Overview
Before starting development, we need to agree on what information should be saved when a student works on a challenge.  
This helps everyone follow the same structure and avoids confusion later.

## What is a submission?
A submission (student answer) is created when a student starts a challenge.  
It is connected to:
- one student
- one challenge

It also keeps track of the student’s work and progress.

## Information that should be saved

| Information | Meaning |
|---|---|
| ID | Unique reference for the answer |
| Challenge ID | Which challenge the answer belongs to |
| Student ID | Which student created the answer |
| Code / Solution | The student’s written solution |
| Status | Current progress state |
| Created date | When the answer was first created |
| Last updated date | When the answer was last changed |

## Rules
- A student can only have one answer per challenge
- Every answer must be connected to both a student and a challenge
- The solution field must be filled before saving or submitting

## Acceptance criteria
- [ ] The team agrees on all the information listed above
- [ ] No additional information is added without team discussion
- [ ] Backend and frontend use the same structure

## Out of scope
This issue does not include:
- Editing or deleting answers
- Mentor comments or feedback
- Grades or scoring

## Note
The current backend structure already includes these fields.  
This issue is only to confirm that the structure is correct before continuing development.