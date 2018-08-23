# Imperio Developer Guide

This document provides information for developing Imperio.

## Workflow

The typical Impero developer workflow goes like this:

1. Clone repo:  
`git clone https://github.com/ncsuandrew12/imperio.git`

1. Checkout base branch, typically `master`:  
`git checkout master`

1. Create new branch, typically `feature/mybranch` or `bugfix/mybranch`:  
`git checkout -b feature/mybranch`

1. Make your code changes.

1. Update any necessary documentation. It's good practice to regenerate the JavaDoc (see the release process for more information), but this is not required.

1. Update the release notes with any noteworthy changes (`docs/RELEASE.md`).

1. Commit and push  
`git add docs/RELEASE.md <other-files>`  
`git commit -m "my comment"`  
`git push --set-upstream origin feature/mybranch`

1. Open a [pull request](https://github.com/ncsuandrew12/imperio/compare)  
base: master (typically)  
compare: feature/mybranch

## Versioning

Imperio release versions are in Major.Minor.Bugfix format.

Any release may make the following changes. However, it is generally recommended to make bugfix releases as low-impact as possible.

1. Add new classes, functions/overloads, fields, or other members
1. Change or remove private or "no modifier" fields or variables (changing variable type, name, etc.)
1. Rename function parameters
1. Change or remove private or "no modifier" classes or functions (renaming, changing implements/extends classes, adding/removing parameters, changing parameter types, changing thrown exceptions, etc.)
1. Modify documentation
1. Remove third-party dependencies and/or import statements

The other version-based limitations are:

1. Major releases may change anything and everything within Imperio.  
**Only** major releases may do the following:
    1. Change names of public or protected classes or functions
    1. Remove public or protected classes or functions
    1. Change or remove public or protected fields or variables
    1. Change the name of the Imperio jar file or other deliverables
    1. Add new third-party dependencies

1. Minor releases **must** be backwards-compatible. That is, applications should not have to recompile their code to switch from 1.0.0 to 1.1.1. However, minor releases may introduce new classes, functions or functionality, and applications may need to be updated and recompiled to take advantage of new classes, functions, etc.

1. Generally speaking, bugfix releases may make any changes that minor releases may make. However, it is **strongly** recommended that bugfix releases be made as low-impact as reasonably possible.

## Release Process

Follow these steps to create a new release of Imperio.

For the purposes of this section, A, B, and C refer to the major, minor, and bugfix release numbers, respectively.

1. Checkout the base branch for the release:  
`git checkout master` **or**  
`git checkout release/A.X`

    1. For major releases, this is `master`
    1. For minor releases, this is `release/A.D`, where `D` is `B - 1`
    1. For bugfix releases, this is `release/A.B`

1. Ensure that the base branch is up-to-date:  
`git pull`

1. If this is a major or minor release, create the new release branch:  
`git checkout -b release/A.X`  
`git push --set-upstream origin release/A.X`

    1. For major releases, this is `release/A.0`
    2. For minor releases, this is `release/A.B`

1. If this is a bugfix release, checkout the existing minor release branch:  
`git checkout release/A.B`

1. Create update branch  
`git checkout -b feature/A.B.C-updates`  
`git push --set-upstream origin feature/A.B.C-updates`

1. Make any necessary changes. For minor or bugfix releases, this will probably involve cherry-picking commits from `master`. If you don't need to make any general changes (typical with major releases), you can skip this step.

    1. After making changes, push to the repo.  
`git push`
    1. Open a [pull request](https://github.com/ncsuandrew12/imperio/compare)  
base: release/A.B  
compare: feature/A.B.C-updates
    1. Once the pull request is approved, merge it. But don't delete the update branch just yet.

1. Make release changes.  
`cd Imperio`

    1. Update the version string in `build.gradle` by modifying the line that looks like this:  
`def versionMain = '0.0.0'`
    1. Commit  
`git add build.gradle`  
`git commit -m "Updated version for A.B.C"`

    1. Update the release notes.  
Make any necessary adjustments to the release notes.  
Add A.B.C to the release notes history section.  
`git add docs/RELEASE.md`  
`git commit -m "Updated release notes for A.B.C"`

    1. Update included JavaDoc.  
`gradle release`  
`git rm -rf docs/javadoc`  
`mkdir docs/javadoc`  
`cd docs/javadoc`  
`jar xf ../../build/libs/Imperio-javadoc.jar`  
`cd ../../`  
`git add docs/javadoc`  
`git commit -m "Updated JavaDoc for A.B.C"`  
`git push`

    1. Open a [pull request](https://github.com/ncsuandrew12/imperio/compare)  
base: release/A.B  
compare: feature/A.B.C-updates
    1. Once the pull request is approved, merge it.
    1. You may delete the update branch. But if a problem is discovered, you'll have to re-create it.

1. Create release tag.  
`git checkout release/A.B`  
`git pull`

    1. If you're redoing the release due to a problem of some kind, first delete the existing tag:  
`git tag -d A.B.C`  
`git push origin :A.B.C`

    1. Create the tag.  
`git tag -a A.B.C -m "Laying A.B.C tag." release/A.B`  
`git push --tags`

You've finished with the release proper. Direct users/adopters to [https://github.com/ncsuandrew12/imperio/tree/A.B.C](https://github.com/ncsuandrew12/imperio/tree/A.B.C)

## Post-Release Process

1. Update documentation branch JavaDoc:  
`git checkout tags/A.B.C`  
`cd Imperio`  
`rm -rf ../../tmp-javadoc`  
`cp -r docs/javadoc ../../tmp-javadoc`  
`git checkout documentation`  
`git pull`  
`git checkout -b doc/A.B.C-doc`  
`git push --set-upstream origin doc/A.B.C-doc`  
`cp -r ../../tmp-javadoc javadoc/A.B.C`  
`cd javadoc`  
`git rm -f A.B`  
`cp -r A.B.C A.B`  
`git rm -f A`  
`cp -r A.B A`  
`git rm -f latest ###` (only if this a major release or a minor/  
`cp -r A latest #` bugfix release for the latest major release)  
`git add A* latest`  
`git commit -m "Added A.B.C JavaDoc"`

1. Update the documentation branch index:  
`cd ../`

    1. Update the release list in [DOCUMENTATION.md](https://github.com/ncsuandrew12/imperio/blob/documentation/Imperio/DOCUMENTATION.md)  
Add this line (with the actual version) to the appropriate place in the release list:  
`1. [A.B.C](https://github.com/ncsuandrew12/imperio/tree/A.B.C)`

    1. Update the JavaDoc list in DOCUMENTATION.md  
Add this line (with the actual version) to the appropriate place in the JavaDoc list:  
`1. [A.B.C](https://htmlpreview.github.io/?https://github.com/ncsuandrew12/imperio/blob/documentation/Imperio/javadoc/A.B.C/index.html)`

1. Open a [pull request](https://github.com/ncsuandrew12/imperio/compare)  
`git add DOCUMENTATION.md`  
`git commit -m "Updated DOCUMENTATION.md for A.B.C"`  
`git push`  
base: documentation  
compare: doc/A.B.C-doc

1. Once the pull request is approved, merge it and delete the update branch.

1. Update the History section of the release notes (`docs/RELEASE.md`) in `master`.

The release is now complete.
