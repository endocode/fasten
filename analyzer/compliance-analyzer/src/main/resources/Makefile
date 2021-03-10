.PHONY: all run clean

###########################################################
# Run the compliance plugin's docker-compose file locally #
###########################################################

all: run clean

run:
	# Starts Quartermaster
	docker-compose \
		up \
		--remove-orphans \
		--renew-anon-volumes \
		--exit-code-from client

clean:
	# Brings down previous execution's resources, if any
	docker-compose \
		down \
		--volumes \
		--remove-orphans
