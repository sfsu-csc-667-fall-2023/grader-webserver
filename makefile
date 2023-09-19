TARBALL=assignment-1-tests.tar
ASSIGNMENT_GRADER=https://raw.githubusercontent.com/sfsu-csc-667-fall-2023/grader-webserver/main/

package-tests:
	@gtar cf $(TARBALL) tests

update-tests:
	@rm -rf tests
	@wget -q $(ASSIGNMENT_GRADER)/$(TARBALL)
	@tar -xf $(TARBALL)
	@rm -f $(TARBALL)