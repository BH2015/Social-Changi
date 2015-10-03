base_location = '/home/darth/Development/code/github.com/BH2015/Social-Changi/app/src/main/res/'
resolutions = ['-hdpi', '-mdpi', '-xhdpi', '-xxhdpi', '-xxxhdpi']
filenames = ['ic_gamepad_{}_24dp.png', 'ic_movie_{}_24dp.png', 'ic_local_florist_{}_24dp.png', 'ic_shopping_basket_{}_24dp.png']

def grey_and_save(image, location, grey_name):
    drawable = pdb.gimp_image_get_active_drawable(image)
    pdb.gimp_brightness_contrast(drawable, -64, 0)
    pdb.file_png_save2(image, drawable, location + grey_name, grey_name, 0, 0, 0, 0, 0, 0, 1, 0, 1)

def do_run():
    for f in filenames:
        white_file = f.format('white')
        for res in resolutions:
            location = base_location + 'drawable' + res + '/'
            image = pdb.file_png_load(location + white_file, white_file)
            grey_and_save(image, location, f.format('grey'))

do_run()
