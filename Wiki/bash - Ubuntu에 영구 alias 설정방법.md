# Creating Custom Commands Using Alias in Ubuntu

In Ubuntu, you can create custom commands using the `alias` command. This can be done by defining a function in the `~/.bashrc` file. This function will register the custom command.

## Custom Function for Creating Permanent Alias

Here is a function named `peras` that you can add to your `~/.bashrc` file:

```bash
function peras() {
    local help=0
    local list=0
    local force=0
    if [ "$1" = "-f" ]; then
        force=1
        shift
    fi

    if [ "$1" = "-h" ]; then
        help=1
        shift
    fi

    if [ "$1" = "-l" ]; then
        list=1
        shift
    fi

    if [ $help -eq 1 ]; then
        echo "Usage: peras [-f] <alias name> <command>"
        echo "Options:"
        echo "  -f: Force overwrite if alias already exists"
        echo "  -l: List all permanent aliases"
        return 0
    fi

    if [ $list -eq 1 ]; then
        peras_ls
        return 0
    fi

    if [ -z "$1" ] || [ -z "$2" ]; then
        echo "Error: Both alias name and command are required"
        return 1
    fi

    if grep -q "^alias $1=" ~/.bash_aliases; then
        if [ $force -eq 1 ]; then
            sed -i "/^alias $1=/d" ~/.bash_aliases
            echo "alias $1='$2'" >> ~/.bash_aliases
            source ~/.bash_aliases
        else
            echo "Error: Alias $1 already exists. Use -f option to overwrite"
            return 1
        fi
    else
        echo "alias $1='$2'" >> ~/.bash_aliases
        source ~/.bash_aliases
    fi
}

function peras_ls() {
    if [ -f ~/.bash_aliases ]; then
        echo "Aliases:"
        grep '^alias ' ~/.bash_aliases | cut -d ' ' -f 2- | sed 's/=/ -> /'
    else
        echo "No aliases found"
    fi
}
```

This code defines two functions: `peras` and `peras_ls`, which are used to manage permanent aliases in Ubuntu's Bash shell.

- The `peras` function allows users to create or update a permanent alias. It takes two arguments: the alias name and the command to be aliased. Optional flags `-f`, `-h`, and `-l` can be used to force overwrite, display help, or list all permanent aliases, respectively.
- The `peras_ls` function lists all the permanent aliases defined in the `~/.bash_aliases` file.

To use these functions, source the `~/.bash_aliases` file after defining or updating aliases.

## Example

Here is an example of how to use the `peras` function to create a custom command:

```bash
peras "lst" "ls -ltr"
```

This command will create a new alias named `lst` that runs the command `ls -ltr`. If the alias already exists, it will return an error. To overwrite the existing alias, use the `-f` option:

```bash
peras -f "lst" "ls -ltr"
```

After running this command, you can use `lst` in the terminal just like any other command.
