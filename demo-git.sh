#!/bin/bash

# LMS CLI DevOps - Git Workflow Demo Script
# This script simulates the Git lifecycle described in the requirements.

echo ">>> Initializing Git Repository..."
git init
git checkout -b main
git add .
git commit -m "chore: initial project structure with maven and java 25"

echo -e "\n>>> Creating Feature Branch: feature/add-borrow-logic..."
git checkout -b feature/add-borrow-logic

# Simulate an edit (adding a comment for demo)
echo "// Feature update" >> src/main/java/com/example/lms/services/LibraryService.java
git add .
git commit -m "feat: implement enhanced borrowing validation"

echo -e "\n>>> Simulating Stashing Workflow..."
echo "// WIP changes" >> src/main/java/com/example/lms/cli/MainCLI.java
git stash push -m "WIP: menu enhancements"
echo ">>> Current Stash List:"
git stash list

echo -e "\n>>> Creating Hotfix Branch..."
git checkout main
git checkout -b hotfix/bugfix-return
echo "// Hotfix applied" >> src/main/java/com/example/lms/services/LibraryService.java
git add .
git commit -m "fix: resolve return book edge case"

echo -e "\n>>> Merging Hotfix to Main..."
git checkout main
git merge hotfix/bugfix-return --no-ff
git branch -d hotfix/bugfix-return

echo -e "\n>>> Rebasing Feature Branch on Latest Main..."
git checkout feature/add-borrow-logic
git rebase main

echo -e "\n>>> Poping Stash..."
git stash pop

echo -e "\n>>> Final Merge to Main..."
git checkout main
git merge feature/add-borrow-logic

echo -e "\n>>> Git Workflow Demo Completed Successfully!"
git log --oneline --graph --all
