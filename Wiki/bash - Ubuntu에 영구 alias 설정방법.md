# Creating Custom Commands Using Alias in Ubuntu

In Ubuntu, you can create custom commands using the `alias` command. This can be done by defining a function in the `~/.bashrc` file. This function will register the custom command.

## Custom Function for Creating Permanent Alias

Here is a function named `peras` that you can add to your `~/.bashrc` file:

```bash
function peras() {
    local force=0
    if [ "$1" = "-f" ]; then
        force=1
        shift
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
```

This function takes two arguments: the alias name and the command. If either of these arguments is missing, the function will return an error message. If the alias already exists in the `~/.bash_aliases` file, the function will check if the `-f` option was used. If it was, the function will overwrite the existing alias with the new command. If the `-f` option was not used, the function will return an error message. If the alias does not exist, the function will append it to the `~/.bash_aliases` file. Finally, the function will source the `~/.bash_aliases` file to make the new alias available immediately.

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